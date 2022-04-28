/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.feature.platform.data.model.persistence;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.justrightcrypto.exchange.features.platform.data.model.persistence.UserAssetEntity;
import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractIdGeneratedEntity;

/**
 * Implementation that maps the "user" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@ToString(of = {"id"})
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
@Getter
@Setter
@Table(name = "user")
@Entity
public class UserEntity extends AbstractIdGeneratedEntity<String> {

    /** GrantedAuthority format as required by Spring Security. */
    private static final String GRANTED_AUTHORITY_FORMAT = "ROLE_{0}";

    /** User name of the user. */
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    /** Password of the user. */
    @Column(name = "password", nullable = false)
    private String password;
}