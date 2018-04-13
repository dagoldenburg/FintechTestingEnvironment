/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webrobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author jakdan
 */
public class Webrobot {

    private WebDriver driver;
    static final String URL = "http://localhost:8080/ExjobbWebsite/";
    public Webrobot(BrowserType type) throws Exception {
        switch (type) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "/Users/jakdan/Downloads/chromedriver");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                break;
            case IE:
                break;
            default:
                throw new Exception("Browser not implemented yet");
        }
    }

    public void MakeSeveralTransactions(int nrTransactions) {
        login();
    }

    public void goToTransferMoney() {
        //Get Transfer Money menu choice and click it
        WebElement transferMoney;
        transferMoney = driver.findElement(By.linkText("Transfer money"));
        transferMoney.click();
    }

    public void goToHistory() {
        WebElement history;
        history = driver.findElement(By.linkText("History"));
        history.click();
    }

    public List<String> getTransactionHistory(int nrTransactions) {
        List<String> transactionList = new ArrayList<>();
        if (nrTransactions < 0) {
            return transactionList;
        }
        WebElement nrTransactionsField;
        nrTransactionsField = driver.findElement(
                By.id("j_idt16:nrTransactions"));
        nrTransactionsField.sendKeys(Integer.toString(nrTransactions));

        WebElement submitButton = driver.findElement(
                By.name("j_idt16:j_idt18"));
        submitButton.click();

        
        for (int i = 0; i < nrTransactions; i++) {
            try {
                WebElement e = driver.findElement(
                        By.xpath("//div[@id= " + (nrTransactions - i) + "]"));
                transactionList.add(e.getText());
            } catch (Exception e) {
                System.out.println("aja");
                e.printStackTrace();
            }
        }
        
        return transactionList;

    }

    public void login() {
        driver.get(URL);
        
        //Get username field and enter username
        WebElement usernameField;
        usernameField = driver.findElement(By.name("j_idt6:username"));
        usernameField.sendKeys("Jubbe");

        //Get password field and enter password
        WebElement pwField;
        pwField = driver.findElement(By.name("j_idt6:password"));
        pwField.sendKeys("abc123");

        //Get login button and click it
        WebElement loginBtn;
        loginBtn = driver.findElement(By.name("j_idt6:knapp"));
        loginBtn.click();
        
        
    }

    public void makeTransaction() {
        //Get username field and enter username
        WebElement toUsername;
        toUsername = driver.findElement(By.name("j_idt16:toUser"));
        toUsername.sendKeys("Dag");

        //Get amount field, randomize value and enter it
        WebElement amount;
        amount = driver.findElement(By.name("j_idt16:amount"));
        Random rand = new Random();
        int money = rand.nextInt(11000) + 1000;
        amount.sendKeys(Integer.toString(money));

        //Get transfer button and click it
        WebElement transferBtn;
        transferBtn = driver.findElement(By.name("j_idt16:transferBtn"));
        transferBtn.click();
    }

    public boolean transactionDidSucceed() {
        if (driver.getPageSource().contains("success")) {
            return true;
        } else {
            return false;
        }
    }

    public void closeDriver() {
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
