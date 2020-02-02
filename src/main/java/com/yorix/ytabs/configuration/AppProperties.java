package com.yorix.ytabs.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Data
public class AppProperties {
    private String imageStorageLocation;
}
