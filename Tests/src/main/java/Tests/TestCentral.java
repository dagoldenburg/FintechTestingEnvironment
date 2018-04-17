package Tests;

import Tests.RestTest;
import Tests.RobotTest;
import Tests.SFTPTest;
import io.vertx.core.Vertx;

import java.util.Scanner;

public class TestCentral {


    public static void main(String[] args) throws InterruptedException {
        TestI t = null;
        System.out.println("What do you want to test?");
        System.out.println("1 rest");
        System.out.println("2 sftp");
        System.out.println("3 robot");
        Scanner scanner = new Scanner(System.in);
        switch(scanner.nextLine()){
            case "1": t = new RestTest();break;
            case "2": t = new SFTPTest();break;
            case "3": t = new RobotTest();break;
            default:
                System.out.println("Not valid input");
        }

        System.out.println("What do you want to test?");
        System.out.println("1 send");
        System.out.println("2 retrieve");
        switch(scanner.nextLine()){
            case "1":
                //warmup
                for(int i = 1;i<=10000;i*=10){
                    t.testSendTransactions(i);
                }
                System.gc();
                Thread.sleep(1000);
                for(int i = 1;i<=10000;i*=10){
                    t.testSendTransactions(i);
                }break;
            case "2":
                //warmup
                for(int i = 1;i<=10000;i*=10){
                    t.testRetrieveTransactions(i);
                }
                System.gc();
                Thread.sleep(1000);
                for(int i = 1;i<=10000;i*=10){
                    t.testRetrieveTransactions(i);
                }break;
            default:
                System.out.println("Invalid input");
                break;
        }

    }
}
