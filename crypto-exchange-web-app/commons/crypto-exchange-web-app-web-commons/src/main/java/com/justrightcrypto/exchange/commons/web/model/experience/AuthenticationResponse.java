/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.commons.web.model.experience;

import com.justrightcrypto.exchange.commons.properties.AbstractPropertiesProvider;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;

/**
 * A data transfer object that is used to return the authentication response and typically contains the Bearer token
 * details.
 *
 * @author Jagannath Mishra
 */
@ToString(of = {"accessToken"})
@EqualsAndHashCode(of = {"accessToken"})
@Data
@Builder
public class AuthenticationResponse extends AbstractPropertiesProvider {
    /** Access token. */
    private String accessToken;

    /** The username of the user. */
    private String username;

    /** Email of the user. */
    private String email;

    /** Collection of roles that are assigned to the concerned user. */
    @Builder.Default
    private Collection<String> roles = new HashSet<>();
}