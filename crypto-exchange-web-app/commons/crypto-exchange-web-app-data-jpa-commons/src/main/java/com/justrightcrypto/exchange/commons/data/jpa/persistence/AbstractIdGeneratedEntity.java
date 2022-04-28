/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.commons.data.jpa.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.justrightcrypto.exchange.commons.data.persistence.IEntity;

/**
 * Abstract implementation of an entity where the value of the primary key is auto-generated based on the supported
 * strategies.
 *
 * @param <ID>
 *         Type of the primary key.
 *
 * @author Jagannath Mishra
 */
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractIdGeneratedEntity<ID extends Serializable> implements IEntity<ID> {
    /** Unique identifier of the entity - typically a primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private ID id;
}