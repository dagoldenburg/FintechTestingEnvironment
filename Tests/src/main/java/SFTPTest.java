import Exceptions.SFTPClientException;
import SFTPLogic.SFTPClient;
import SFTPLogic.SFTPDataGenerator;

public class SFTPTest extends TestI{

    public SFTPTest(String filename) {
        super(filename);
    }

    @Override
    void testSendTransactions(int amountOfTests) {
        super.setFileNameEnding("SFTPSendManyTrans"+amountOfTests);
        SFTPDataGenerator dg = new SFTPDataGenerator();
        String fname = dg.generateTransactionBatch(amountOfTests);
        SFTPClient sftpc =
                new SFTPClient("localhost",22, "do",
                        "JakobENoob123#\"!",2222);
        try {
            Thread t = new Thread(new Measurement(filename));
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
