package worker;

import java.util.HashSet;
import java.util.Set;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.contrib.pattern.ClusterClient;
import akka.contrib.pattern.ClusterSingletonManager;
import akka.contrib.pattern.ClusterSingletonPropsFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Address joinAddress = startBackend(null, "backend");
    Thread.sleep(5000);
//    startBackend(joinAddress, "backend");
//    Thread.sleep(5000);
//    startBackend(joinAddress, "backend");
//    Thread.sleep(5000);
//    startBackend(joinAddress, "backend");
//    Thread.sleep(5000);
//    startBackend(joinAddress, "backend");
//    Thread.sleep(5000);
//    startBackend(joinAddress, "backend");
    startWorker(joinAddress);
    Thread.sleep(5000);
    startFrontend(joinAddress);
//    startFrontend(joinAddress);
//    startFrontend(joinAddress);
  }

  private static String systemName = "Workers";
  private static FiniteDuration workTimeout = Duration.create(10, "seconds");

  public static Address startBackend(Address joinAddress, String role) {
    Config conf = ConfigFactory.parseString("akka.cluster.roles=[" + role + "]").
      withFallback(ConfigFactory.load());
//	Config conf = ConfigFactory.load();
    ActorSystem system = ActorSystem.create(systemName, conf);
    Address realJoinAddress =
      (joinAddress == null) ? Cluster.get(system).selfAddress() : joinAddress;
    Cluster.get(system).join(realJoinAddress);
//    for(int i = 0; i < 10000; i++){
//    	 system.actorOf(ClusterSingletonManager.defaultProps("active",
//    		      PoisonPill.getInstance(), role, new ClusterSingletonPropsFactory() {
//    		      public Props create(Object handOverData) {
//    		        return Master.props(workTimeout);
//    		      }
//    		    }), "master"+i);
//    }
    system.actorOf(ClusterSingletonManager.defaultProps("active",
		      PoisonPill.getInstance(), role, new ClusterSingletonPropsFactory() {
		      public Props create(Object handOverData) {
		        return Master.props(workTimeout);
		      }
		    }), "master");
    return realJoinAddress;
  }

  public static void startWorker(Address contactAddress) {
    ActorSystem system = ActorSystem.create(systemName);
    Set<ActorSelection> initialContacts = new HashSet<ActorSelection>();
    initialContacts.add(system.actorSelection(contactAddress + "/user/receptionist"));
    ActorRef clusterClient = system.actorOf(ClusterClient.defaultProps(initialContacts),
      "clusterClient");
    system.actorOf(Worker.props(clusterClient, Props.create(WorkExecutor.class)), "worker");
  }

  public static void startFrontend(Address joinAddress) {
    ActorSystem system = ActorSystem.create(systemName);
    Cluster.get(system).join(joinAddress);
    ActorRef frontend = system.actorOf(Props.create(Frontend.class), "frontend");
//    for(int i = 0; i < 100; i++)
//    {
//    	 system.actorOf(Props.create(WorkProducer.class, frontend), "producer"+i);
//    }
    system.actorOf(Props.create(WorkProducer.class, frontend), "producer");
    system.actorOf(Props.create(WorkResultConsumer.class), "consumer");
  }
}
