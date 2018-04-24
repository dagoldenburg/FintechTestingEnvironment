package Tests;

import Tests.RestTest;
import Tests.RobotTest;
import Tests.SFTPTest;
import io.vertx.core.Vertx;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.exit;

public class TestCentral {


    public static void main(String[] args) throws InterruptedException {
        TestI t = null;

        System.out.println("What do you want to test?");
        System.out.println("1 rest");
        System.out.println("2 sftp");
        System.out.println("3 robot");
        Scanner scanner = new Scanner(System.in);

        String date = new Date().toString();
        String folderName = "";
        boolean scan = true;
        while(scan){
            switch(scanner.nextLine()) {
                case "1":
                    folderName = "RestTest - "+ date;
                    t = new RestTest(folderName,true);
                    scan = false;
                    break;
                case "2":
                    folderName = "SFTPTest - " + date;
                    t = new SFTPTest(folderName,true);
                    scan = false;
                    break;
                case "3":
                    folderName = "RobotTest - " + date;
                    t = new RobotTest(folderName,true);
                    scan = false;
                    break;
                default:
                    System.out.println("Not valid input");
            }
        }
        scan = true;
        File dir = new File("TestResults/" + folderName);
        boolean success = dir.mkdir();
        if(success){
            System.out.println("What do you want to test?");
            System.out.println("1 send");
            System.out.println("2 retrieve");
            while(scan) {
                switch (scanner.nextLine()) {
                    case "1":
                        //warmup
                        for (int i = 1; i <= 1000; i *= 10) {
                            t.testSendTransactions(i);
                        }
                        System.gc();
                        Thread.sleep(1000);

                        for (int i = 1; i <= 1000; i *= 10) {
                            t.testSendTransactions(i);
                        }
                        scan = false;
                        break;
                    case "2":
                        //warmup
                        for (int i = 1; i <= 1000; i *= 10) {
                            t.testRetrieveTransactions(i);
                        }
                        System.gc();
                        Thread.sleep(1000);

                        for (int i = 1; i <= 1000; i *= 10) {
                            t.testRetrieveTransactions(i);
                        }
                        scan = false;
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
        }else{
            System.out.println("Failed to create new folder");
        }




    }
}
