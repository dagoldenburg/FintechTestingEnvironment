package Tests;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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
           // tests.add(new SFTPTest(SFTPTestFolder));
            tests.add(new RestTest(RestTestFolder));
          //  tests.add(new RobotTest(RobotTestFolder));

            //WARMUP
            System.out.println("WARMING UP");
            for(TestI t : tests){
                for(int i=1;i<=100;i*=10)
                    t.testSendTransactions(i);
                for(int i=1;i<=100;i*=10)
                    t.testRetrieveTransactions(i);
            }


            //REAL TESTS
            System.out.println("STARTING REAL TESTS");
            for(TestI t : tests){
                for(int i=1;i<=100;i*=10)
                    t.testSendTransactions(i);
                for(int i=1;i<=100;i*=10)
                    t.testRetrieveTransactions(i);
            }

            System.out.println("Klar med testerna");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
