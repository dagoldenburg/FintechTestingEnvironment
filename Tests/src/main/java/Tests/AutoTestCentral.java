package Tests;

import java.util.Date;

public class AutoTestCentral {

    public static void main(String[] args){

        TestI testInterface;
        String date = new Date().toString();
        TestI[] tests = {new SFTPTest("SFTPTest - " + date),
                new RestTest("RestTest - "+ date),
                new RobotTest("RobotTest - " + date)};

        for(TestI t : tests){
            for(int i=1;i<=10000;i*=10)
                t.testSendTransactions(i);
            for(int i=1;i<=10000;i*=10)
                t.testRetrieveTransactions(i);
        }
    }

}
