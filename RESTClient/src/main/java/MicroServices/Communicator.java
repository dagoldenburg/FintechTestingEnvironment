package MicroServices;

import Model.Transaction;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.util.LinkedList;

public class Communicator extends AbstractVerticle {

    WebClient client;
    String myToken;
    private static final String SERVERIP = "localhost";
    @Override
    public void start() {
        client = WebClient.create(vertx);

        sendLoginForm();

    }

    int amountOfTests;
    boolean send;
    private Thread t;

    public Communicator(int amountOfTests,boolean send,Thread t){
        this.amountOfTests = amountOfTests;
        this.send = send;
        this.t = t;
    }

    public void sendLoginForm(){
        System.out.println();
        MultiMap form = MultiMap.caseInsensitiveMultiMap();
        form.set("username","dagge");
        form.set("password","dagge");
        client.post(7089,SERVERIP,"/rest/login")
                .sendForm(form,ar -> {
                    if(ar.succeeded()){
                        HttpResponse<Buffer> response = ar.result();
                        myToken = response.bodyAsString();
                        /*if(send) {
                            for (int i = 0; i < amountOfTests; i++) {
                                makeTransaction();
                            }
                        }else{
                            for (int i = 0; i < amountOfTests; i++) {
                                getNrOfTransactions();
                            }
                        }
                        try {
                            t.interrupt();
                        }catch(NullPointerException e){

                        }*/
                    }
                });
    }

    private void getAllTransactions(){
        client.get(7089,SERVERIP,"/rest/getAllTransactions").addQueryParam("name","dag")
                .send(ar -> succeededTransaction(ar));
    }

    public void getNrOfTransactions(){
        client.get(7089,SERVERIP,"/rest/getNrOfTransactions")
                .addQueryParam("name","dag")
                .addQueryParam("nrOfTransactions","10")
                .addQueryParam("token",myToken)
                .send(ar-> succeededTransaction(ar));
    }

    private void succeededTransaction(AsyncResult<HttpResponse<Buffer>> ar) {
        if(ar.succeeded()) {
            HttpResponse<Buffer> response = ar.result();
            parseJsonArrayToTransactionList(response);
        }
    }

    public void makeTransaction(){
        MultiMap form = MultiMap.caseInsensitiveMultiMap();
        form.set("usernameTo","dag");
        form.set("usernameFrom","jakob");
        form.set("amount","123.45");

        client.post(7089,SERVERIP,"/rest/makeTransaction")
                .addQueryParam("token",myToken)
                .sendForm(form,ar -> {
                    if(ar.succeeded()){
                        HttpResponse<Buffer> response = ar.result();
                    }else{
                        System.out.println("FAIIIILLLL: " + ar.cause().getMessage());
                    }
                });
    }

    private void getUsers(){
        client.get(7089,SERVERIP,"/rest/getUsers")
                .addQueryParam("token",myToken)
                .send(ar->{
                    if(ar.succeeded()){
                        HttpResponse<Buffer> response = ar.result();
                        LinkedList<String> names = new LinkedList<>();
                        for(Object s : response.bodyAsJsonArray()) {
                            names.add((String) s);
                            System.out.println((String)s);
                        }
                    }
                });
    }

    private LinkedList<Transaction> parseJsonArrayToTransactionList(HttpResponse<Buffer> response){
        LinkedList<Transaction> transactions = new LinkedList<>();
        System.out.println(response.bodyAsString());
        for(Object s : response.bodyAsJsonArray()) {
            JsonObject ns = ((JsonObject) s);
            transactions.add(new Transaction(
                    ns.getDouble("amount"),
                    ns.getString("to"),
                    ns.getString("from"),
                    ns.getInteger("transactionId")
            ));
        }
        for(Transaction t:transactions){
            System.out.println(t.toString());
        }
        return transactions;
    }

}
