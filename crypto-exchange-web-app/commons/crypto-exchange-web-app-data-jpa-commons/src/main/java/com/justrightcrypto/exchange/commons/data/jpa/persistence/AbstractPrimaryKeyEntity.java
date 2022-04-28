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

import com.justrightcrypto.exchange.commons.data.persistence.IEntity;

import java.io.Serializable;

/**
 * Abstract implementation of an entity where the value of the primary key.
 *
 * @author Jagannath Mishra
 */
public abstract class AbstractPrimaryKeyEntity<ID extends Serializable> implements IEntity<ID> {

}