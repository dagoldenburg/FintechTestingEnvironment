abstract class TestI implements Cloneable{

    String filename;

    public TestI(String filename){
        this.filename = filename;
        setUpEnvironment();
    }

    abstract void testSendTransactions(int amountOfTests);

    abstract void testRetrieveTransactions(int amountOfTests);

    abstract void setUpEnvironment();

    void setFileNameEnding(String specificFileName){
        filename=specificFileName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
