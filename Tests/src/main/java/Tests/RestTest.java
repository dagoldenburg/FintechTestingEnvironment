package Tests;

import DB.PostGreSQLDb;
import OgHTTPClient.HTTPRequests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;
import io.vertx.core.Vertx;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class RestTest extends TestI implements Cloneable{

    private String folderName;


    public RestTest(String folderName){

        this.folderName = folderName;
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        System.out.println("Testing rest send transactions : " + amountOfTransactions);
        super.setFileNameEnding("RestSendManyTrans"+ amountOfTransactions);

        ArrayList<MeasureResult> results = new ArrayList<>();

        System.gc();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < 20; i ++){ //do test 20 times and calculate average
            //Create measure thread object and start the thread
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
            am.setIsRunning(true);
            t.start();

            //Execute the operations
            HTTPRequests h = new HTTPRequests(ServerInfo.getServerIp());
            h.login();
            for(int k = 0;k<amountOfTransactions;k++) {
                h.makeTransaction();
            }
            h.disconnect();

            //Terminate the thread and store results.
            am.setIsRunning(false);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            results.add(am.getResult());

            //clear database
          //  super.clearDatabase();
        }
        //write to file
        new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));
    }


    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("Rest test: retrieving transactions ( " + 10 + ")");
        super.setFileNameEnding("RestRetrieveManyTrans"+amountOfTransactions);

        System.gc();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<MeasureResult> results = new ArrayList<>();
        for(int i = 0; i < 20; i++){ //do test 20 times and calculate average
            /** ONE LIFECYCLE **/
            HTTPRequests h = new HTTPRequests(ServerInfo.getServerIp());
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
            am.setIsRunning(true);
            t.start();
            h.login();
            h.retrieveTransaction("name=dag&nrOfTransactions=" + amountOfTransactions);
            h.disconnect();

            am.setIsRunning(false);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(am.getResult() == null){
                System.out.println("Result is null m8.........");
            }else{
                results.add(am.getResult());
            }

            /*******************/
            //clear db
           // super.clearDatabase();
        }

        new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));
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
