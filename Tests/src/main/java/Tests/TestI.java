package Tests;

import DB.PostGreSQLDb;
import OgHTTPClient.HTTPRequests;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;

import java.util.ArrayList;

abstract class TestI implements Cloneable{

    private String filename;
    private String folderName;

    abstract void sendTransactions(int amountOfTransactions);
    abstract void retrieveTransactions(int amountOfTransactions);



    abstract void testSendTransactions(int amountOfTransactions);

    abstract void testRetrieveTransactions(int amountOfTransactions);

    abstract void setUpEnvironment();

    void setFileNameEnding(String specificFileName){
        filename=specificFileName;
    }

    public void setFolderName(String folderName){
        this.folderName = folderName;
    }

    public String getFilename() {
        return filename;
    }

    public String getFolderName() {
        return folderName;
    }

    public void clearDatabase(){
        PostGreSQLDb db = new PostGreSQLDb();
        db.createConnection();
        db.truncateTransactionTable();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
