package com.n2.sdk.config;

import com.n2.console.N2Console;
import com.n2.core.store.N2Store;
import com.n2.sdk.models.N2Properties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class N2ConsoleConfig {

    private final N2Properties n2Properties;
    private final N2Store n2Store;

    public N2ConsoleConfig(N2Properties n2Properties, N2Store n2Store) {
        this.n2Properties = n2Properties;
        this.n2Store = n2Store;
    }

    @PostConstruct
    public void startConsoleIfEnabled() {
        if (!n2Properties.isConsoleEnabled()) {
            log.info("We will not be starting the N2 UI. To enable it, set 'n2.console-enabled=true'");
            return;
        }
        log.info("Starting N2 UI Console on port={}", n2Properties.getPort());
        N2Console.start(n2Properties.getPort(), n2Store);
    }
}
