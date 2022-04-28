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
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.justrightcrypto.exchange.commons.data.persistence.ISoftDeletable;

/**
 * Abstract implementation of an entity that supports soft-deletion functionality against an entity whose primary key is
 * not auto-generated.
 *
 * @param <ID>
 *         Type of the primary key.
 *
 * @author Jagannath Mishra
 */
@ToString(callSuper = true, of = {})
@EqualsAndHashCode(callSuper = true, of = {})
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSoftDeletableEntity<ID extends Serializable> extends AbstractEntity<ID> implements ISoftDeletable {
    /** Boolean to indicate if this record is deleted or not. */
    @Column(name = "deleted")
    private boolean deleted;

    /** Timestamp in epoch format indicating when the record was deleted. */
    @Column(name = "deleted_ts")
    private Long deletedTimestamp;
}