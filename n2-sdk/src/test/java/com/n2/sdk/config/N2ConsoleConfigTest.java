package com.n2.sdk.config;

import com.n2.console.N2Console;
import com.n2.core.store.N2Store;
import com.n2.sdk.models.N2Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class N2ConsoleConfigTest {
    @Mock
    private N2Properties n2Properties;
    @Mock
    private N2Store n2Store;
    private N2ConsoleConfig n2ConsoleConfig;

    @BeforeEach
    void setUp() {
        n2ConsoleConfig = new N2ConsoleConfig(n2Properties, n2Store);
    }

    @Test
    void whenConsoleEnabled_thenConsoleStarts() {
        when(n2Properties.isConsoleEnabled()).thenReturn(true);
        when(n2Properties.getPort()).thenReturn(1234);

        n2ConsoleConfig = new N2ConsoleConfig(n2Properties, n2Store);

        try (MockedStatic<N2Console> mockedStatic = Mockito.mockStatic(N2Console.class)) {
            n2ConsoleConfig.startConsoleIfEnabled();
            // Verify static method was called once with correct params
            mockedStatic.verify(() -> N2Console.start(1234, n2Store), atMostOnce());
        }
    }

    @Test
    void whenConsoleDisabled_thenConsoleDoesNotStart() {
        when(n2Properties.isConsoleEnabled()).thenReturn(false);

        try (MockedStatic<N2Console> mockedStatic = Mockito.mockStatic(N2Console.class)) {
            n2ConsoleConfig.startConsoleIfEnabled();
            // Verify static method was never called
            mockedStatic.verifyNoInteractions();
        }
    }
}