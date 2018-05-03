package Tests;

import Exceptions.SFTPClientException;
import SFTPLogic.SFTPClient;
import SFTPLogic.SFTPDataGenerator;
import Tests.FileHandler.CsvWriter;
import Tests.Measuring.MeasureResult;

import java.util.ArrayList;

public class SFTPTest extends TestI{


    public SFTPTest(String folderName,boolean warmup){
        super.setWarmup(warmup);
        super.setFolderName(folderName);
    }

    @Override
    void sendTransactions(int amountOfTransactions) {
        SFTPClient sftpc = null;
        try{
            SFTPDataGenerator dg = new SFTPDataGenerator();
            String fname = dg.generateTransactionBatch(amountOfTransactions);
            sftpc =
                    new SFTPClient(ServerInfo.getServerIp(),22, ServerInfo.getSFTPUsername(),
                            ServerInfo.getSFTPPassword(),2222);
            sftpc.connect();
            sftpc.uploadFile("/Users/do/IdeaProjects/ExjobbMonkaS/Hejhej/"+fname,
                    "/Users/do/Documents/REQUESTDOCUMENTS/"+fname);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(sftpc != null){
                sftpc.disconnect();
            }
        }

    }

    @Override
    void retrieveTransactions(int amountOfTransactions) {
        SFTPClient sftpc = null;
        try {
            sftpc = new SFTPClient(ServerInfo.getServerIp(),22,ServerInfo.getSFTPUsername(),ServerInfo.getSFTPPassword(),2222);
            sftpc.connect();
            sftpc.retrieveFile("dag",amountOfTransactions);
        } catch (SFTPClientException e) {
            e.printStackTrace();
        }finally{
            if(sftpc != null){
                sftpc.disconnect();
            }
        }
    }

    @Override
    void testSendTransactions(int amountOfTransactions) {
        System.out.println("SFTP test: send transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("SFTPSendManyTrans"+ amountOfTransactions);

        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.SEND,super.isWarmup());
        System.out.println("DONE WITH SFTP SEND TRANSACTIONS TEST");

    }

    @Override
    void testRetrieveTransactions(int amountOfTransactions) {
        System.out.println("SFTP test: retrieve transactions (" + amountOfTransactions + ")");
        super.setFileNameEnding("SFTPRetrieveManyTrans"+amountOfTransactions);

        MeasureLoop.measure(this,amountOfTransactions, MeasureLoop.TestType.RECEIVE,super.isWarmup());
        System.out.println("DONE WITH SFTP RETRIEVE TRANSACTIONS TEST");

    }


    @Override
    void setUpEnvironment() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
