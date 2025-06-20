package com.n2.sdk.config;

import com.n2.core.store.N2Builder;
import com.n2.core.store.N2Store;
import com.n2.sdk.models.N2Properties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

/***
 * Injects a starter bean for clients to load in their in memory document store
 */
@Configuration
@EnableConfigurationProperties(N2Properties.class)
@Slf4j
public class N2Config {
    N2Properties n2Properties;

    public N2Config(N2Properties n2Properties) {
        this.n2Properties = n2Properties;
    }

    /***
     * Creates the concrete implementation of {@link N2Store}
     * @return {@link N2Store}
     */
    @Bean
    @SneakyThrows
    public N2Store n2Store() {
        log.info("Creating N2Store using props={}", n2Properties);

        Path seedPath = null;
        Path schemaPath = null;

        try {
            Resource seedResource = new ClassPathResource(n2Properties.getSeed());
            if (seedResource.exists()) {
                seedPath = seedResource.getFile().toPath();
            }
        } catch (Exception e) {
            log.warn("Could not load seed resource '{}', falling back to open", n2Properties.getSeed(), e);
        }

        try {
            Resource schemaResource = new ClassPathResource(n2Properties.getSchema());
            if (schemaResource.exists()) {
                schemaPath = schemaResource.getFile().toPath();
            }
        } catch (Exception e) {
            log.warn("Could not load schema resource '{}'", n2Properties.getSchema(), e);
        }

        return N2Builder.builder()
                .seed(seedPath)
                .schema(schemaPath)
                .type(n2Properties.getType())
                .build();
    }
}
