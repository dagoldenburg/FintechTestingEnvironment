package Tests;
import webrobot.BrowserType;
import webrobot.Webrobot;
import webrobot.WebrobotTests;

public class RobotTest extends TestI implements Cloneable{
    @Override
    void testSendTransactions(int amountOfTransactions) {
        super.setFileNameEnding("RetrieveManyTrans"+amountOfTransactions);
        WebrobotTests wt = new WebrobotTests();
        Thread t = new Thread(new Measurement(filename));
        t.start();
        wt.makeSeveralTransactions(amountOfTransactions);
        t.interrupt();
    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        super.setFileNameEnding("RetrieveManyTrans"+amountOfTransactions);
        WebrobotTests wt = new WebrobotTests();
        Thread t = new Thread(new Measurement(filename));
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
