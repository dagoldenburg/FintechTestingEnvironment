package Tests;

import DB.PostGreSQLDb;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class AutoTestCentral {

    public static void main(String[] args){

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

           tests.add(new SFTPTest(SFTPTestFolder,true));
           // tests.add(new RestTest(RestTestFolder));
           // tests.add(new RobotTest(RobotTestFolder));

            //WARMUP
            System.out.println("WARMING UP");
            for(TestI t : tests){
                t.testSendTransactions(10);
                t.testRetrieveTransactions(10);
                t.testSendTransactions(10);
                t.testRetrieveTransactions(10);
                t.testSendTransactions(10);
                t.testRetrieveTransactions(10);
            }

            for(TestI t : tests){
                t.setWarmup(false);
            }
            System.out.println("STARTING REAL TESTS");
            for(TestI t : tests){

                t.testSendTransactions(1);

                for(int i = 10; i <= 100; i+=10){
                    t.testSendTransactions(i);
                }

                t.testRetrieveTransactions(1);

                for(int i = 10; i <= 100; i+=10){
                    t.testRetrieveTransactions(i);
                }
            }

            System.out.println("Klar med testerna");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
