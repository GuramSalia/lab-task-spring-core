package com.epam.labtaskspringcore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

// Initialize Storage with Prepared Data from File: Implement a Spring bean post-processor to initialize the storage
// with data from a file during application start.

@Component
public class StorageInitializer implements BeanPostProcessor {

    @Value("${file.path}")
    private String filePath;
    private final InMemoryStorage storage;

    public StorageInitializer(InMemoryStorage storage) {this.storage = storage;}

    // Constructor injection of InMemoryStorage bean

    // Implement postProcessAfterInitialization method to read data from the file
}

