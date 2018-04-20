package Tests;

import DB.PostGreSQLDb;
import Tests.Measuring.MeasureResult;

import java.util.ArrayList;

abstract class TestI implements Cloneable{

    String filename;

    abstract void testSendTransactions(int amountOfTransactions);

    abstract void testRetrieveTransactions(int amountOfTransactions);

    abstract void setUpEnvironment();

    void setFileNameEnding(String specificFileName){
        filename=specificFileName;
    }

    public MeasureResult getAverageResult(ArrayList<MeasureResult> results){
        double totalCpu = 0;
        double totalRam = 0;
        double totalOperationTime = 0;

        for(MeasureResult m : results){
            totalCpu += m.getCpuUsage();
            totalRam += m.getRamUsage();
            totalOperationTime += m.getOperationTime();
        }
        int size = results.size();
        return new MeasureResult(totalCpu/size,totalRam/size,totalOperationTime/size);

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
