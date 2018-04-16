package Tests;

import Exceptions.SFTPClientException;
import SFTPLogic.SFTPClient;
import SFTPLogic.SFTPDataGenerator;

public class SFTPClientTests {
    static final String SERVERIP = "10.46.1.90";


    /**
     * Returns average time for x nrTransactions to be sent.
     * @param nrTransactions
     * @param nrTimes
     * @return
     */
    public long makeTransactionsTest(int nrTransactions,int nrTimes){

        try{
            long startTime = System.currentTimeMillis();

            for(int i = 0; i < nrTimes; i++){
                SFTPDataGenerator dg = new SFTPDataGenerator();
                String filename = dg.generateTransactionBatch(nrTransactions);
                SFTPClient sftpc = new SFTPClient(SERVERIP,22,"do","JakobENoob123#\"!",2222);
                try {
                    sftpc.connect();
                    try {
                        sftpc.uploadFile(filename,
                                "/Users/do/Documents/REQUESTDOCUMENTS/"+filename);
                    } catch (SFTPClientException e) {
                        e.printStackTrace();
                    }
                } catch (SFTPClientException e) {
                    System.out.println("Connection failed, quitting");
                    return 0;
                }finally{
                    sftpc.disconnect();
                }
            }

            long finTime = System.currentTimeMillis();
            long avgTime = (finTime-startTime)/nrTimes;
            return avgTime;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public long getTransactionHistoryTest(int nrTimes, int nrTransactions){
        return 0;
    }

}
