/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.features.platform.data.model.experience.user;

import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Jagannath Mishra
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class User {
    /** Unique identifier of the user. */
    private String id;

    /** Username of the user. */
    private String username;

    /** Password of the user. */
    private String password;

    /** Reference to the email. */
    private String email;

    /** Reference to the passwordExpiryTimestamp. */
    private String passwordExpiryTimestamp;

    /** Reference to the fullName. */
    private String fullName;

    /** Reference to the tenantId. */
    private String tenantId;
}