package com.n2.sdk.models;

import com.n2.core.model.StoreType;
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
    /***
     * Schema file
     */
    private String schema;
    /***
     * type
     */
    private StoreType type;
}
