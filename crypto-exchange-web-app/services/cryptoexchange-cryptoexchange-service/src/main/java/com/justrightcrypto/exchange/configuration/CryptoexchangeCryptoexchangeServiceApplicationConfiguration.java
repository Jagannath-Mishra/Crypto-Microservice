/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of cryptoexchange-cryptoexchange-service.
 *
 * cryptoexchange-cryptoexchange-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.justrightcrypto.exchange.commons.web.configuration.WebConfiguration;
import com.justrightcrypto.exchange.commons.web.configuration.properties.ApiDocumentationSettings;
import com.justrightcrypto.exchange.commons.web.configuration.properties.ApplicationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class responsible to configure the beans, etc. for the microservice that contains this configuration.
 *
 * @author Jagannath Mishra
 */
@Import(value = {WebConfiguration.class})
@PropertySource("classpath:/l10n/ValidationMessages.properties")
@PropertySource("classpath:/l10n/error_messages.properties")
@EnableJpaRepositories(basePackages = {"com.justrightcrypto.exchange.features.platform.data.repository"})
@EntityScan(basePackages = {"com.justrightcrypto.exchange.features.platform.data.model.persistence"})
@Configuration
public class CryptoexchangeCryptoexchangeServiceApplicationConfiguration {
    /**
     * Bean that captures the API documentation settings.
     *
     * @return Bean instance of type {@link ApiDocumentationSettings}.
     */
    @ConfigurationProperties(prefix = "com.justrightcrypto.configuration")
    @Bean
    public ApiDocumentationSettings apiDocumentationSettings() {
        return new ApiDocumentationSettings();
    }

    /**
     * Bean that captures the application security settings.
     *
     * @return Bean instance of type {@link ApplicationProperties}.
     */
    @ConfigurationProperties(prefix = "spring")
    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }
}