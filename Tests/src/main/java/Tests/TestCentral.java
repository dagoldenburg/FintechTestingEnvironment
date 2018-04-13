package Tests;

public class TestCentral {


    public static void main(String[] args) {
        /*Tests.TestI[] tests = {new Tests.SFTPTest("SFTP"),new Tests.RestTest("Rest"),new Tests.RobotTest("Robot")};
        for(Tests.TestI t : tests){
            for(int i = 1;i<10000;i*=10) {
                t.testSendTransactions(i);
                t.testRetrieveTransactions(i);
            }
        }*/
        TestI t = new SFTPTest("SFTP");
         for(int i = 1;i<=10000;i*=10){
            t.testSendTransactions(i);
        }
        //Tests.TestI t = new Tests.RestTest("REST");
        //for(int i = 1;i<=10000;i*=10){
           // t.testSendTransactions(1);
        //}

        //vertx.deployVerticle(new Communicator(1,true));

    }
}
