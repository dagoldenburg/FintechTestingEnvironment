package Tests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;
import webrobot.BrowserType;
import webrobot.Webrobot;
import webrobot.WebrobotTests;

import java.awt.*;
import java.util.ArrayList;

public class RobotTest extends TestI implements Cloneable{


    private String folderName;

    public RobotTest(String folderName ){

        this.folderName = folderName;
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        System.out.println("Robot test: send transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("RobotSendManyTrans"+amountOfTransactions);

        System.gc();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<MeasureResult> results = new ArrayList<>();

        for(int i = 0; i < 20; i++){ //do test 20 times and calculate avg
            /** ONE LIFECYCLE **/
            WebrobotTests wt = new WebrobotTests(ServerInfo.getServerIp());
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
            am.setIsRunning(true);
            t.start();
            wt.makeSeveralTransactions(amountOfTransactions);

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
            /******************/
            //clear db
          //  super.clearDatabase();
        }

        //write to file

            new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));

            System.out.println("KLAR MED ROBOT TEST SEND TRANSACTIONS");



    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("Robot test: retrieve transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("RobotRetrieveManyTans"+amountOfTransactions);

        System.gc();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<MeasureResult> results = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            /** ONE LIFECYCLE **/
            WebrobotTests wt = new WebrobotTests(ServerInfo.getServerIp());
            AverageMeasurement am = new AverageMeasurement(folderName,filename);
            Thread t = new Thread(am);
            am.setIsRunning(true);
            t.start();
            wt.getTransactionHistory(amountOfTransactions);

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
          //  super.clearDatabase();
        }


            //write to file
            new CsvWriter().writeToFile(folderName + filename, super.getAverageResult(results));

            System.out.println("KLAR MED ROBOT TEST RETRIEVE TRANSACTIONS");




    }


    @Override
    void setUpEnvironment() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
