package Tests;

abstract class TestI implements Cloneable{

    String filename;

    abstract void testSendTransactions(int amountOfTransactions);

    abstract void testRetrieveTransactions(int amountOfTransactions);

    abstract void setUpEnvironment();

    void setFileNameEnding(String specificFileName){
        filename=specificFileName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
