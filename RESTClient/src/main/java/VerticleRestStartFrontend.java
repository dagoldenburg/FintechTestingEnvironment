import Tests.Measurement;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class VerticleRestStartFrontend extends AbstractVerticle {

    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();

        //warmup
        for(int i = 1;i<=10000;i*=10){
            vertx.deployVerticle(new Communicator(i,true,null));
        }
        System.gc();
        Thread.sleep(1000);
        for(int i = 1;i<=10000;i*=10){
            Thread t = new Thread(new Measurement("RestSendManyTrans"+i));
            t.start();
            vertx.deployVerticle(new Communicator(i,true,t));
            Thread.sleep(500);
        }

    }

}
