package Tests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;
import webrobot.BrowserType;
import webrobot.Webrobot;
import webrobot.WebrobotTests;

import java.awt.*;
import java.util.ArrayList;

public class RobotTest extends TestI implements Cloneable{



    public RobotTest(String folderName ){
        super.setFolderName(folderName);
    }

    @Override
    void sendTransactions(int amountOfTransactions) {
        WebrobotTests wt = new WebrobotTests(ServerInfo.getServerIp());
        wt.makeSeveralTransactions(amountOfTransactions);
    }

    @Override
    void retrieveTransactions(int amountOfTransactions) {
        WebrobotTests wt = new WebrobotTests(ServerInfo.getServerIp());
        wt.getTransactionHistory(amountOfTransactions);
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        System.out.println("Robot test: send transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("RobotSendManyTrans"+amountOfTransactions);

        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.SEND);



        System.out.println("KLAR MED ROBOT TEST SEND TRANSACTIONS");



    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("Robot test: retrieve transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("RobotRetrieveManyTans"+amountOfTransactions);

        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.RECEIVE);

        System.out.println("KLAR MED ROBOT TEST RETRIEVE TRANSACTIONS");
    }


    @Override
    void setUpEnvironment() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
