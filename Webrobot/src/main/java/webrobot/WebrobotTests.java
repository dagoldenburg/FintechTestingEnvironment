/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webrobot;

import java.util.logging.Level;
import java.util.logging.Logger;
import Tests.*;
/**
 *
 * @author jakdan
 */
public class WebrobotTests {

    public void makeOneTransactionTest(int nrTimes) {
        try {
            //One transaction at a time test

            long startTime = System.currentTimeMillis();
             
            Thread t = new Thread(new Measurement("WebRobotSendTransaction"+nrTimes));
            t.start();
            for (int i = 0; i < nrTimes; i++) {
                makeOneTransaction();
            }
            t.interrupt();
            long finTime = System.currentTimeMillis();
            long timeTaken = finTime - startTime;
            System.out.println("ONE TRANSACTION TEST");
            System.out.println(nrTimes + " operations took: " + timeTaken + " ms.");
            System.out.println("Average time: " + (timeTaken / nrTimes));

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeSeveralTransactionsTest(int nrTimes, int nrTransactions) {
        try { //Several transactions at a time test
            long startTime = System.currentTimeMillis();
           
            for (int i = 0; i < nrTimes; i++) {
                makeSeveralTransactions(nrTransactions);
            }
            long finTime = System.currentTimeMillis();
            long timeTaken = finTime - startTime;
            System.out.println("SEVERAL (" + nrTransactions + ") TRANSACTIONS TEST");
            System.out.println(nrTimes + " operations took: " + timeTaken + " ms.");
            System.out.println("Average time: " + (timeTaken / nrTimes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getTransactionHistoryTest(int nrTimes, int nrTransactions){
        try{
            long startTime = System.currentTimeMillis();
            
            for(int i = 0; i < nrTimes; i++){
                getTransactionHistory(nrTransactions);
            }
            long finTime = System.currentTimeMillis();
            long timeTaken = finTime - startTime;
            System.out.println("SEVERAL (" + nrTransactions + ") TRANSACTION HISTORY TEST");
            System.out.println(nrTimes + " operations took: " + timeTaken + " ms.");
            System.out.println("Average time: " + (timeTaken/nrTimes));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void makeOneTransaction() {
        Webrobot robot = null;
        try {
            robot = new Webrobot(BrowserType.CHROME);
            robot.login();
            robot.goToTransferMoney();
            robot.makeTransaction();
            if (robot.transactionDidSucceed()) {
                System.out.println("Success!");
            } else {
                System.out.println("Fail :<");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //close browser
            if (robot != null) {
                robot.closeDriver();
            }

        }
    }

    public void makeSeveralTransactions(int nrTransactions) {
        Webrobot robot = null;
        try {
            robot = new Webrobot(BrowserType.CHROME);
            robot.login();
            robot.goToTransferMoney();

            for (int i = 0; i < nrTransactions; i++) {
                robot.makeTransaction();
                if (robot.transactionDidSucceed()) {
                    System.out.println("Success!");
                } else {
                    System.out.println("Fail :<");
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(WebrobotTests.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //close browser
            if (robot != null) {
                robot.closeDriver();
            }
        }
    }

    public void getTransactionHistory(int nrTransactions) {
        Webrobot robot = null;
        try {
            robot = new Webrobot(BrowserType.CHROME);
            robot.login();
            robot.goToHistory();
            for (String s : robot.getTransactionHistory(nrTransactions)) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (robot != null) {
                robot.closeDriver();
            }
        }
    }
}
