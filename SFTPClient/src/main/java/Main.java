import Exceptions.SFTPClientException;
import SFTPLogic.SFTPClient;
import SFTPLogic.SFTPDataGenerator;

public class Main {

    static final String SERVERIP = "";
    public static void main(String[] args){

        SFTPDataGenerator dg = new SFTPDataGenerator();
        String filename = dg.generateTransactionBatch(100);
        SFTPClient sftpc = new SFTPClient(SERVERIP,22,"do","JakobENoob123#\"!",2222);
            try {
                sftpc.connect();
            } catch (SFTPClientException e) {
                System.out.println("Connection failed, quitting");
                return;
            }
        /*try {
            sftpc.uploadFile("/Users/do/IdeaProjects/ExjobbMonkaSrevert/Hejhej/"+filename,
                    "/Users/do/Documents/REQUESTDOCUMENTS/"+filename);

        } catch (SFTPClientException e) {
            e.printStackTrace();
        }*/
        //sftpc.retrieveFile();
        sftpc.disconnect();
    }
}
