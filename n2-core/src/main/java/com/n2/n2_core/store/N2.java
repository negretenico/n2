package com.n2.n2_core.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class N2 {
    public static N2Store open(){
        return new InMemory(new ConcurrentHashMap<>(), new ObjectMapper());
    }
    public static N2Store openWithResource(Path resource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return new InMemory(mapper.readValue(new File(resource.toUri()),new TypeReference<Map<String, Object>>() {}),mapper);
    }
}
