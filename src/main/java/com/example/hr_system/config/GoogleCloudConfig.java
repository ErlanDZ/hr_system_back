package com.example.hr_system.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleCloudConfig {

    @Bean
    public Storage storage() {
        return StorageOptions.newBuilder()
                .setProjectId("bbc8d0ced8d3a5cd8e883be9f94f64ba5d93c381")
                .build()
                .getService();
    }
}
