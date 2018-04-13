import io.vertx.core.Vertx;

public class TestCentral {


    public static void main(String[] args) {
        /*TestI[] tests = {new SFTPTest("SFTP"),new RestTest("Rest"),new RobotTest("Robot")};
        for(TestI t : tests){
            for(int i = 1;i<10000;i*=10) {
                t.testSendTransactions(i);
                t.testRetrieveTransactions(i);
            }
        }*/
        TestI t = new SFTPTest("SFTP");
         for(int i = 1;i<=10000;i*=10){
            t.testSendTransactions(i);
        }
        //TestI t = new RestTest("REST");
        //for(int i = 1;i<=10000;i*=10){
           // t.testSendTransactions(1);
        //}

        //vertx.deployVerticle(new Communicator(1,true));

    }
}
