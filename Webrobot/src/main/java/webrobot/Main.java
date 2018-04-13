/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webrobot;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author jakdan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        System.out.println("Hello worlderino");

        int nrTimes = 10;
        int nrTransactions = 10;
        
        WebrobotTests tester = new WebrobotTests();

        long historyAvgTime = tester.getTransactionHistoryTest(nrTimes, 10);
        long oneTxsAvgTime = tester.makeOneTransactionTest(nrTimes);
        long severalTxsAvgTime = tester.makeSeveralTransactionsTest(nrTimes, nrTransactions);


        ///Print results
        System.out.println();
        System.out.println("******** TEST RESULTS *********");
        System.out.println("-- HISTORY TEST --");
        System.out.println("Number of transactions collected each lifecycle: " + nrTransactions);
        System.out.println("Average time per lifecycle: " + historyAvgTime);
        System.out.println();

        System.out.println("-- ONE TRANSACTION TEST --");
        System.out.println("Average time per lifecycle: " + oneTxsAvgTime);
        System.out.println();

        System.out.println("-- SEVERAL TRANSACTION TEST --");
        System.out.println("Number transactions sent each lifecycle: " + nrTransactions);
        System.out.println("Average time per lifecycle: " + severalTxsAvgTime);
        System.out.println();
        System.out.println("********************************");
    }
}
