package Tests;

import DB.PostGreSQLDb;
import OgHTTPClient.HTTPRequests;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class AutoTestCentral {

    public static void main(String[] args){

        String startTime = new Date().toString();
        TestI testInterface;
        String date = new Date().toString();

        String testFolder = "TestResults/";

        String SFTPTestFolder = testFolder + date + "/SFTPTest/";
        String RestTestFolder = testFolder + date + "/RestTest/";
        String RobotTestFolder = testFolder + date + "/RobotTest/";


        File thisTestFolder = new File(testFolder + date);
        File SFTPFile = new File(SFTPTestFolder);
        File RestFile = new File(RestTestFolder);
        File RobotFile = new File(RobotTestFolder);

        try{
            thisTestFolder.mkdir();
            SFTPFile.mkdir();
            RestFile.mkdir();
            RobotFile.mkdir();

            ArrayList<TestI> tests = new ArrayList<>();

            //1, 10, 25, 50, 100, 250, 500

          //  tests.add(new RobotTest(RobotTestFolder,true));
            //tests.add(new RestTest(RestTestFolder,true));
            tests.add(new SFTPTest(SFTPTestFolder,true));

            //WARMUP
            System.out.println("WARMING UP");
            for(TestI t : tests){
             //   t.testSendTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(10);
            }

            for(TestI t : tests){
                t.setWarmup(false);
            }

            System.out.println("STARTING REAL TESTS");
            for(TestI t : tests){
               // t.testRetrieveTransactions(250);
             //   t.testSendTransactions(100);
                t.testRetrieveTransactions(1);
                t.testRetrieveTransactions(5);
                t.testRetrieveTransactions(10);
                t.testRetrieveTransactions(25);
                t.testRetrieveTransactions(50);
                t.testRetrieveTransactions(100);
                t.testRetrieveTransactions(250);
                t.testRetrieveTransactions(500);
                t.testRetrieveTransactions(1000);
            }

            System.out.println("Klar med testerna");

            String finishTime = new Date().toString();


            System.out.println("Test start time: " + startTime);
            System.out.println("Test finish time: " + finishTime);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
