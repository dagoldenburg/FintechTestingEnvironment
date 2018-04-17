package MicroServices;

import Tests.AverageMeasurement;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.io.File;
import java.util.Date;

public class VerticleRestStartFrontend extends AbstractVerticle {

    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();

        //warmup
        for(int i = 1;i<=10;i++){
            vertx.deployVerticle(new Communicator(i,true,null));
        }
        System.gc();
        Thread.sleep(1000);
        String folderName = "RestTest - " + new Date().toString();
        File f = new File("TestResults/" + folderName);
        boolean success = f.mkdir();
        if(success){
            for(int i = 1;i<=10;i++){
                Thread t = new Thread(new AverageMeasurement(folderName,"RestSendManyTrans"+i));
                t.start();
                vertx.deployVerticle(new Communicator(i,true,t));
                //   Thread.sleep(500);
            }
        }else{
            System.out.println("Failed to create folder: " + folderName);
        }


    }

}
