// src/main/java/com/example/App.java
package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", (exchange -> {
            String response = "✅ Hello from Java App in the browser!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }));
        server.setExecutor(null); // default
        server.start();
        System.out.println("Server started at http://localhost:8080/");
    }
}
