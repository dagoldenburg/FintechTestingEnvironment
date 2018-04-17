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
        switch(scanner.nextLine()){
            case "1":
                System.out.println("This doesnt work ya twerp");
                exit(1);
                    break;
            case "2":
                folderName = "SFTPTest - " + date;
                t = new SFTPTest(folderName);
                break;
            case "3":
                folderName = "RobotTest - " + date;
                t = new RobotTest(folderName);
                break;
            default:
                System.out.println("Not valid input");
                exit(1);
        }

        File dir = new File("TestResults/" + folderName);
        boolean success = dir.mkdir();

        if(success){
            System.out.println("What do you want to test?");
            System.out.println("1 send");
            System.out.println("2 retrieve");
            switch(scanner.nextLine()){
                case "1":

                    //warmup
                    for(int i = 1;i<=10;i++){
                        t.testSendTransactions(i);
                    }
                    System.gc();
                    Thread.sleep(1000);
                    for(int i = 1;i<=10;i++){
                        t.testSendTransactions(i);
                    }break;
                case "2":
                    //warmup
                    for(int i = 1;i<=10;i++){
                        t.testRetrieveTransactions(i);
                    }
                    System.gc();
                    Thread.sleep(1000);
                    for(int i = 1;i<=10;i++){
                        t.testRetrieveTransactions(i);
                    }break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }else{
            System.out.println("Failed to create new folder");
        }



    }
}
