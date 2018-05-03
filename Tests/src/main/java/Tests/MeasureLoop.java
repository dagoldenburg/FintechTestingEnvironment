package Tests;

import OgHTTPClient.HTTPRequests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;

import java.io.IOException;
import java.util.ArrayList;

public class MeasureLoop {

    public enum TestType{
        SEND, RECEIVE;
    }

    public static void measure(TestI test,int amountOfTransactions,TestType testType,boolean warmup){
        ArrayList<MeasureResult> results = new ArrayList<>();
        for(int i = 0; i < 20; i ++){ //do test 20 times and calculate average
            //Create measure thread object and start the thread

            garbageCollect(warmup);

            AverageMeasurement am = new AverageMeasurement();
            Thread t = startThread(am);

            //Execute the operations
            switch(testType){
                case SEND: test.sendTransactions(amountOfTransactions); break;
                case RECEIVE: test.retrieveTransactions(amountOfTransactions); break;
                default: break;
            }

            //Terminate the thread and store results.
            terminateThread(am,t);
            results.add(am.getResult());


            //  super.clearDatabase();
        }
        //write to file
        new CsvWriter().writeToFile(test.getFolderName() + test.getFilename(), results);
    }

    public static Thread startThread(AverageMeasurement am){
        Thread t = new Thread(am);
        am.setIsRunning(true);
        t.start();
        return t;
    }

    public static void garbageCollect(boolean warmup){
        if(!warmup) {
            System.gc();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void terminateThread(AverageMeasurement am, Thread t){
        am.setIsRunning(false);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
