package Tests;

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
        System.out.println("Rest test: sending transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("RestSendManyTrans"+ amountOfTransactions);

        //WARMUP
        ArrayList<MeasureResult> results = new ArrayList<>();

        for(int i = 0; i < 100; i ++){ //do test 20 times and calculate average
            /** ONE LIFECYCLE **/
            HTTPRequests h = new HTTPRequests();
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
            t.start();
            h.login();
            for(int k = 0;k<amountOfTransactions;k++) {
                System.out.println("Sending transaction");
                h.makeTransaction();
                System.out.println("Did send transaction");
            }
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
            /********************/
        }
        //write to file

            System.out.println("DONE WITH TESTS: TRYING TO WRITE TO FILE");
            new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));



        System.out.println("KLAR MED TEST SEND TRANSACTIONS");
    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("Rest test: retrieving transactions ( " + 10 + ")");
        super.setFileNameEnding("RestRetrieveManyTrans"+amountOfTransactions);

        ArrayList<MeasureResult> results = new ArrayList<>();
        for(int i = 0; i < 100; i++){ //do test 20 times and calculate average
            /** ONE LIFECYCLE **/
            HTTPRequests h = new HTTPRequests();
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
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
        }


            new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));
            System.out.println("KLAR MED TEST RETRIEVE TRANSACTIONS");



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
