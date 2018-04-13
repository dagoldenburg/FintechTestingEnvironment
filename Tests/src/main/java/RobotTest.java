public class RobotTest extends TestI implements Cloneable{


    public RobotTest(String filename) {
        super(filename);
    }

    @Override
    void testSendTransactions(int amountOfTests) {
        super.setFileNameEnding("RetrieveManyTrans"+amountOfTests);
    }

    @Override
    void testRetrieveTransactions(int amountOfTests) {

        super.setFileNameEnding("RetrieveManyTrans"+amountOfTests);
    }


    @Override
    void setUpEnvironment() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
