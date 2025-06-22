package com.n2.console;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2.core.store.InMemory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class Main {
    public static void main(String args[]) {
        N2Console.start(8080, new InMemory(new ConcurrentHashMap<>(), new ObjectMapper()));
    }
}
