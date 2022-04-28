/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.features.platform.data.model.persistence;

import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.UserAssetTypeEntity;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Implementation that maps the "user_asset" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true, of = {"id"})
@ToString(callSuper = true, of = {"id", "name"})
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "user_asset")
@Entity
public class UserAssetEntity extends AbstractUUIDGeneratedEntity {
    /** Name of an user asset. */
    @Column(name = "name", unique = true, nullable = false, length = 64)
    private String name;

    /** Path/Location of an user asset where it resides. */
    @Column(name = "path", nullable = false, length = 1024)
    private String path;

    /** Unique code of an user asset type (e.g. PROFILE_PICTURE). */
    @ManyToOne
    @JoinColumn(name = "type_code", referencedColumnName = "code")
    private UserAssetTypeEntity type;
}