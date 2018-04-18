package Tests;

import MicroServices.Communicator;
import OgHTTPClient.HTTPRequests;
import io.vertx.core.Vertx;

import java.net.HttpURLConnection;

public class RestTest extends TestI implements Cloneable{

    private String folderName;

    public RestTest(String folderName){
        this.folderName = folderName;
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        super.setFileNameEnding("RestSendManyTrans"+ amountOfTransactions);
        HTTPRequests h = new HTTPRequests();
        Thread t = new Thread(new AverageMeasurement(folderName,filename));
        t.start();
        h.login();
        for(int i = 0;i<amountOfTransactions;i++) {
            h.retrieveTransaction("name=dag&nrOfTransactions=10");
        }
        h.disconnect();
        t.interrupt();
    }

    @Override
    void testRetrieveTransactions(int amountOfTests) {
        super.setFileNameEnding("RestRetrieveManyTrans"+amountOfTests);
        HTTPRequests h = new HTTPRequests();
        Thread t = new Thread(new AverageMeasurement(folderName,filename));
        t.start();
        h.login();
        for(int i = 0;i<amountOfTests;i++) {
            h.retrieveTransaction("name=dag&nrOfTransactions=10");
        }
        h.disconnect();
        t.interrupt();
    }



    @Override
    void setUpEnvironment() {
        /*MicroServices vertx = MicroServices.vertx();
        vertx.deployVerticle(new BigBoiVertx());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
