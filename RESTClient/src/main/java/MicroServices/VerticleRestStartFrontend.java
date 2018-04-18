package MicroServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.io.File;
import java.util.Date;

public class VerticleRestStartFrontend extends AbstractVerticle {

    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Communicator(1,true,null));


    }

}
