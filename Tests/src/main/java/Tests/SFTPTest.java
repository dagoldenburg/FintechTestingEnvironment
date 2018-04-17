package Tests;

import Exceptions.SFTPClientException;
import SFTPLogic.SFTPClient;
import SFTPLogic.SFTPDataGenerator;

public class SFTPTest extends TestI{

    private String folderName;

    public SFTPTest(String folderName){
        this.folderName = folderName;
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        super.setFileNameEnding("SFTPSendManyTrans"+ amountOfTransactions);
        SFTPDataGenerator dg = new SFTPDataGenerator();
        String fname = dg.generateTransactionBatch(amountOfTransactions);
        SFTPClient sftpc =
                new SFTPClient("localhost",22, "do",
                        "JakobENoob123#\"!",2222);
        try {
            Thread t = new Thread(new AverageMeasurement(folderName,filename));
            t.start();
            sftpc.connect();
            sftpc.uploadFile("/Users/do/IdeaProjects/sftpclient/files/"+fname,
                    "/Users/do/Documents/REQUESTDOCUMENTS/"+fname);
            sftpc.disconnect();
            t.interrupt();
        } catch (SFTPClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    void testRetrieveTransactions(int amountOfTests) {
        super.setFileNameEnding("SFTPRetrieveManyTrans"+amountOfTests);
    }

    @Override
    void setUpEnvironment() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
