package Tests;

import DB.PostGreSQLDb;
import OgHTTPClient.HTTPRequests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;
import io.vertx.core.Vertx;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class RestTest extends TestI implements Cloneable{




    public RestTest(String folderName,boolean warmup){
        super.setWarmup(warmup);
        super.setFolderName(folderName);
    }


    @Override
    void sendTransactions(int amountOfTransactions) {
        //Execute the operations
      //  System.out.println("Sending transactions: " + amountOfTransactions);
        HTTPRequests h = null;
        try{
            h = new HTTPRequests(ServerInfo.getServerIp());
            h.login();
            for(int k = 0;k<amountOfTransactions;k++) {
                h.makeTransaction();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(h != null){
                h.disconnect();
            }
        }


    }

    @Override
    void retrieveTransactions(int amountOfTransactions) {
        HTTPRequests h = null;
     //   System.out.println("Receiving transactions: " + amountOfTransactions);
        try{
            h = new HTTPRequests(ServerInfo.getServerIp());
            h.login();
            h.retrieveTransaction("name=dag&nrOfTransactions=" + amountOfTransactions);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(h != null){
                h.disconnect();
            }
        }
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        System.out.println("Testing rest send transactions : " + amountOfTransactions);
        super.setFileNameEnding("RestSendManyTrans"+ amountOfTransactions);
        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.SEND,super.isWarmup());
    }


    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("Rest test: retrieving transactions ( " + amountOfTransactions + ")");
        super.setFileNameEnding("RestRetrieveManyTrans"+amountOfTransactions);

        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.RECEIVE,super.isWarmup());

        System.out.println("KLAR MED TEST RETRIEVE TRANSACTIONS");
    }



    @Override
    void setUpEnvironment() {
        /*MicroServices vertx = MicroServices.vertx();
        vertx.deployVerticle(new Rest.BigBoiVertx());
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
