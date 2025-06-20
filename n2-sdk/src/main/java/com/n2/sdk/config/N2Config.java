package com.n2.sdk.config;

import com.n2.core.store.N2;
import com.n2.core.store.N2Store;
import com.n2.sdk.models.N2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.Objects;

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
    public N2Store n2Store() {
        if (Objects.nonNull(n2Properties.getSeed())) {
            try {
                log.info("Attempting to create instance using seed={}",
                        n2Properties.getSeed());
                return N2.openWithResource(Path.of(n2Properties.getSeed()));
            } catch (Exception e) {
                log.error("Encountered an error reading in the seed={}, " +
                        "error={}", n2Properties.getSeed(), e.getLocalizedMessage());
                return N2.open();
            }
        }
        log.info("No seed provided, using default");
        return N2.open();
    }
}
