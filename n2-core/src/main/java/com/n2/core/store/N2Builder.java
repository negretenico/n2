package com.n2.core.store;

import com.n2.core.exceptions.SchemaValidation;
import com.n2.core.model.StoreType;
import com.n2.core.validator.DocumentValidator;
import com.networknt.schema.ValidationMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class N2Builder {
    private Path seed;
    private Path schema;
    private StoreType type = StoreType.IN_MEMORY; // default

    public static N2Builder builder() {
        return new N2Builder();
    }

    public N2Builder seed(Path seed) {
        this.seed = seed;
        return this;
    }

    public N2Builder schema(Path schema) {
        this.schema = schema;
        return this;
    }

    public N2Builder type(StoreType storeType) {
        this.type = storeType;
        return this;
    }
    private boolean validateSchema(){
        Objects.requireNonNull(this.seed,"Require an inital seeidng to " +
                "perform validation");
        try (InputStream json = Files.newInputStream(this.seed);
             InputStream schemaStream = Files.newInputStream(this.schema)) {
            log.info("Performing Validation using schema={} on seed={}",
                    schema,seed);
            return DocumentValidator.validate(json, schemaStream).isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read seed or schema file", e);
        }
    }
    @SneakyThrows
    public N2Store build()  {
        if(Objects.nonNull(this.schema) && !validateSchema()){
            throw new SchemaValidation("Failed to validate the schema");
        }
        log.info("Successful validation");
        switch (Objects.requireNonNullElse(type, StoreType.IN_MEMORY)) {
            case IN_MEMORY -> {
                return Optional.ofNullable(seed).map(path->{
                    try{
                        return N2.openWithResource(path);
                    }catch (Exception e){
                        return N2.open();
                    }
                }).orElseGet(N2::open);
            }
            case PERSISTENT -> {
                // TODO: implement persistent store in the future
                throw new UnsupportedOperationException("Persistent storage not implemented yet.");
            }
            default -> throw new IllegalStateException("Unsupported store type: " + type);
        }
    }
}
