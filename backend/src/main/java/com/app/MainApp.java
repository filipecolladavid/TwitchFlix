package com.app;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {

    public static final String BASE_URI = "http://localhost:8080/";

    public static Server startServer() {

        // scan packages
        final ResourceConfig config = new ResourceConfig().packages("com.app");

        // final ResourceConfig config = new ResourceConfig(MyResource.class);

        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

    }

    public static void main(String[] args) {

        try {

            final Server server = startServer();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
                }
            }));

            System.out.printf("Application started.%nStop the application using CTRL+C%n");

            // block and wait shut down signal, like CTRL+C
            Thread.currentThread().join();

            // alternative
            // Thread.sleep(Long.MAX_VALUE);       // sleep forever...
            // Thread.sleep(Integer.MAX_VALUE);    // sleep around 60+ years

        } catch (InterruptedException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
