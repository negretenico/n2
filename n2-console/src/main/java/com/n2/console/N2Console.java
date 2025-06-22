package com.n2.console;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2.core.model.Result;
import com.n2.core.store.N2Store;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class N2Console {
    public static void start(int port, N2Store store) {
        ObjectMapper mapper = new ObjectMapper();
        ResourceHandler staticHandler = Handlers.resource(
                        new ClassPathResourceManager(N2Console.class.getClassLoader(), "console/"))
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(false);
        HttpHandler apiHandler = exchange -> {
            switch (exchange.getRequestPath()) {
                case String s when s.equals("/delete") -> {
                    exchange.getRequestReceiver().receiveFullString((ex, msg) -> {
                        log.info("We are deleting key={}", msg);
                        Result<?> result = store.delete(msg);
                        ex.getResponseSender().send(result.toString());
                    });
                }
                case String s when s.equals("/post") -> {
                    exchange.getRequestReceiver().receiveFullString((ex, msg) -> {
                        try {
                            log.info("We are creating an entry {}", msg);
                            Map<String, Object> data = mapper.readValue(msg, new TypeReference<>() {
                            });
                            String key = data.get("key").toString();
                            Object value = data.get("value");

                            Result<?> result = store.put(key, value);
                            ex.getResponseSender().send(result.toString());
                        } catch (Exception e) {
                            ex.setStatusCode(400);
                            ex.getResponseSender().send("Invalid JSON: " + e.getMessage());
                        }
                    });
                }
                case String s when s.equals("/query") -> {
                    exchange.getRequestReceiver().receiveFullString((ex, msg) -> {
                        log.info("We are looking for key={}", msg);
                        Result<?> result = store.query(msg, Object.class);
                        ex.getResponseSender().send(result.toString());
                    });
                }
                default ->
                        exchange.getResponseSender().send("N2 Console is running");

            }
        };

        // Combine static + API paths
        PathHandler pathHandler = Handlers.path()
                .addPrefixPath("/ui", staticHandler)       // UI available at /ui/
                .addPrefixPath("/", apiHandler);           // API available at /

        Undertow server = Undertow.builder()
                .addHttpListener(port, "localhost")
                .setHandler(pathHandler)
                .build();
        server.start();
        System.out.println("N2 Console started at http://localhost:" + port);
    }
}
