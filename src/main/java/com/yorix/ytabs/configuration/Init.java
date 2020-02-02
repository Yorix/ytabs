package com.yorix.ytabs.configuration;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Init {
    private final AppProperties appProperties;

    public Init(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init() throws IOException {
        String imageStorageLocation = appProperties.getImageStorageLocation();
        Path location = Paths.get(System.getProperty("user.home").concat(imageStorageLocation));
        Files.createDirectories(location);
    }
}
