/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.commons.web.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.justrightcrypto.exchange.commons.web.FeatureScanner;
import com.justrightcrypto.exchange.commons.web.configuration.properties.CORSProperties;

@ComponentScan(basePackageClasses = {FeatureScanner.class})
@Import(value = {ApiDocumentationConfiguration.class})
@EnableConfigurationProperties(value = {CORSProperties.class})
@Configuration
public class WebConfiguration {
    /**
     * This method attempts to create a singleton instance of {@link RestTemplate}.
     *
     * @return Instance of type {@link RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}