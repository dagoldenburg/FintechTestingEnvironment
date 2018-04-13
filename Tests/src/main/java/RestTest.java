import io.vertx.core.Vertx;

public class RestTest extends TestI implements Cloneable{

    public RestTest(String filename) {
        super(filename);
        setUpEnvironment();
    }


    @Override
    void testSendTransactions(int amountOfTests) {
        super.setFileNameEnding("RestSendManyTrans"+amountOfTests);
        new Thread(new Measurement(filename)).start();
        Vertx vertx = Vertx.vertx();
        //vertx.deployVerticle(new Communicator(amountOfTests,true));

    }

    @Override
    void testRetrieveTransactions(int amountOfTests) {
        super.setFileNameEnding("RestRetrieveManyTrans"+amountOfTests);
        new Thread(new Measurement(filename)).start();
        Vertx vertx = Vertx.vertx();
        //vertx.deployVerticle(new Communicator(amountOfTests,false));
    }



    @Override
    void setUpEnvironment() {
        /*Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BigBoiVertx());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
