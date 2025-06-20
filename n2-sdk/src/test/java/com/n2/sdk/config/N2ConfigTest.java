package com.n2.sdk.config;

import com.n2.sdk.models.N2Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class N2ConfigTest {
    @Mock
    N2Properties n2Properties;
    N2Config n2Config;

    @BeforeEach
    void setUp() {
        n2Config = new N2Config(n2Properties);
    }

    private void setUpMock(String value) {
        when(n2Properties.getSeed()).thenReturn(value);
    }

    @Test
    void givenANullSeedSettingThenIShouldUseTheN2Open() {
        setUpMock(null);
        n2Config.n2Store();
        assertNotNull(n2Config.n2Store());
    }

    @Test
    void givenASeedSettingThenIShouldUseN2WithResource() {
        setUpMock("seed.json");
        assertNotNull(n2Config.n2Store());
    }

    @Test
    void givenAnErroneousSeedSeeingThenIShouldUseN2Open() {
        setUpMock("/#21.json");
        n2Config.n2Store();
        assertNotNull(n2Config.n2Store());
    }
}