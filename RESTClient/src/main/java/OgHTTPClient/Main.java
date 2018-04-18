package OgHTTPClient;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        HTTPRequests h = new HTTPRequests();
        h.login();
        h.makeTransaction();
        h.retrieveTransaction("name=dag&nrOfTransactions=10");
        h.disconnect();
    }
}
