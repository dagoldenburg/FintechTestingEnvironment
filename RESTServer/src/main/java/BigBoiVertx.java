import DB.DbI;
import DB.Model.Transaction;
import DB.PostGreSQLDb;
import DB.Model.Transaction;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedList;

public class BigBoiVertx extends AbstractVerticle {

    private static String ROOT = "/rest/";
    DbI dbReference = new PostGreSQLDb();

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type","text/html").end("<h1>yoyo</h1>");
        });
        router.route(HttpMethod.POST, "/*").handler(BodyHandler.create());
        router.get(ROOT+"tjo").handler(this::sayTjo);
        router.get(ROOT+"getAllTransactions").handler(this::getAllTransactions);
        router.post(ROOT+"makeTransaction").handler(this::makeTransactions);
        router.post(ROOT+"login").handler(this::authenticateUser);
        router.get(ROOT+"getUsers").handler(this::getUsers);
        router.get(ROOT+"removeToken").handler(this::removeToken);
        router.get(ROOT+"getNrOfTransactions").handler(this::getNrOfTransactions);
        vertx.createHttpServer().requestHandler(router::accept).listen(7089);
        dbReference.createConnection();

    }
    private void sayTjo(RoutingContext rc){
        rc.response().setStatusCode(201).putHeader("content-type","text/html").end("tjo");
    }

    private void authenticateUser(RoutingContext rc) {
            String string = rc.getBodyAsString();
            System.out.println(rc.getBodyAsString());

            String[] strings = string.split("&");
            String username = strings[0].split("=")[1];
            String password = strings[1].split("=")[1];
            if (dbReference.authenticateUser(username, password)) {
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[20];
                random.nextBytes(bytes);
                byte[] encodeByte = Base64.getEncoder().encode(bytes);
                String token = new String(encodeByte);
                dbReference.saveToken(token);
                rc.response().setStatusCode(200).putHeader("content-type", "text/html").end(token);
            } else
                rc.response().setStatusCode(401).putHeader("content-type", "text/html").end("Unsuccessful login!");
    }

    private void getAllTransactions(RoutingContext rc){
        if(dbReference.matchToken(rc.request().getParam("token"))) {
            String username = rc.request().getParam("name");
            LinkedList<Transaction> list = (LinkedList) dbReference.retrieveAllTransactions(username);
            if (list != null) {
                rc.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(list));
            } else {
                rc.response().setStatusCode(204).putHeader("content-type", "text/html").end("No data BOI");
            }
        }else{
            System.out.println("NO TOKERINO");
        }
    }

    private void getNrOfTransactions(RoutingContext rc){
        if(dbReference.matchToken(rc.request().getParam("token"))) {
            String username = rc.request().getParam("name");
            int nrOfTransaction = Integer.parseInt(rc.request().getParam("nrOfTransactions"));
            LinkedList<Transaction> list = (LinkedList) dbReference.retrieveNrOfTransactions(username, nrOfTransaction);
            if (list != null) {
                rc.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(list));
            } else
                rc.response().setStatusCode(204).putHeader("content-type", "text/html").end("No data BOI");
        }else{
            System.out.println("NO TOKERINO");
        }
    }

    private void makeTransactions(RoutingContext rc){
            if(dbReference.matchToken(rc.request().getParam("token"))) {
                String string = rc.getBodyAsString();
                String[] strings = string.split("&");
                String usernameTo = strings[0].split("=")[1];
                String usernameFrom = strings[1].split("=")[1];
                String amount = strings[2].split("=")[1];
                if (dbReference.makeTransaction(usernameTo, usernameFrom, Double.parseDouble(amount))) {
                    rc.response().setStatusCode(200).putHeader("content-type", "text/html").end("GOOD REQ");
                } else
                    rc.response().setStatusCode(400).putHeader("content-type", "text/html").end("BAD REQ");
            }else{
                System.out.println("NO TOKERINO");
            }

    }

    private void getUsers(RoutingContext rc){
        if(dbReference.matchToken(rc.request().getParam("token"))) {
            String string = rc.getBodyAsString();
            LinkedList<String> list = (LinkedList) dbReference.retrieveAllUsernames();
            if (list != null) {
                rc.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(list));
            } else
                rc.response().setStatusCode(204).putHeader("content-type", "text/html").end("No data BOI");
        }else{
            System.out.println("NO TOKERINO");
        }

    }

    public void removeToken(RoutingContext rc){
        if(dbReference.matchToken(rc.request().getParam("token"))){
            dbReference.removeToken(rc.request().getParam("token"));
        }else
            System.out.println("NO TOKERINO");

    }

}
