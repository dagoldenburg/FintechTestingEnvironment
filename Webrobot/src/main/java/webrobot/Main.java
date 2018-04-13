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
        
      //  tester.getTransactionHistoryTest(nrTimes, 10);
        tester.makeOneTransactionTest(nrTimes);
      //  tester.makeSeveralTransactionsTest(nrTimes, nrTransactions);
    }
}
