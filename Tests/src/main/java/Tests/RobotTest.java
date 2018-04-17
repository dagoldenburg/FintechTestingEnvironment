package Tests;
import webrobot.BrowserType;
import webrobot.Webrobot;
import webrobot.WebrobotTests;

import java.awt.*;

public class RobotTest extends TestI implements Cloneable{


    private String folderName;

    public RobotTest(String folderName){
        this.folderName = folderName;
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        super.setFileNameEnding("RobotSendManyTrans"+amountOfTransactions);
        WebrobotTests wt = new WebrobotTests();
        Thread t = new Thread(new AverageMeasurement(folderName,filename));
        t.start();
        wt.makeSeveralTransactions(amountOfTransactions);
        t.interrupt();
    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        super.setFileNameEnding("RobotRetrieveManyTans"+amountOfTransactions);
        WebrobotTests wt = new WebrobotTests();
        Thread t = new Thread(new AverageMeasurement(folderName,filename));
        t.start();
        wt.getTransactionHistory(amountOfTransactions);
        t.interrupt();
    }


    @Override
    void setUpEnvironment() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
