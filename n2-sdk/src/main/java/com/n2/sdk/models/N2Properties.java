package com.n2.sdk.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * Represents the seed file used to load data upon application start
 */
@ConfigurationProperties(prefix = "n2")
@Data
public class N2Properties {
    /***
     * Seed file location
     */
    private String seed;
}
