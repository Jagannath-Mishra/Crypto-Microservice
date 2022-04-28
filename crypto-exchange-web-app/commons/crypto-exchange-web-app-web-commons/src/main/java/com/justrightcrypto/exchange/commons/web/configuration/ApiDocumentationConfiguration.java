/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.commons.web.configuration;

import com.justrightcrypto.exchange.commons.web.configuration.properties.ApiDocumentationSettings;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration file for Swagger's documentation.
 *
 * @author
 */
@EnableConfigurationProperties(value = {ApiDocumentationSettings.class})
@Configuration
public class ApiDocumentationConfiguration {
    /** API documentation properties. */
    private final ApiDocumentationSettings apiDocumentationSettings;

    /**
     * Constructor.
     *
     * @param apiDocumentationSettings API Documentation configuration.
     */
    public ApiDocumentationConfiguration(final ApiDocumentationSettings apiDocumentationSettings) {
        this.apiDocumentationSettings = apiDocumentationSettings;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final ApiDocumentationSettings.Contact contact = apiDocumentationSettings.getContact();

        // API Information
        final Info info =
                new Info()
                        .title(apiDocumentationSettings.getTitle())
                        .version(apiDocumentationSettings.getVersion())
                        .contact(
                                new Contact()
                                        .name(contact.getName())
                                        .email(contact.getEmail())
                                        .url(contact.getUrl()))
                        .license(
                                new License()
                                        .name(apiDocumentationSettings.getLicense())
                                        .url(apiDocumentationSettings.getLicenseUrl()));

        // Return the configuration object.
        return new OpenAPI().info(info);
    }
}
