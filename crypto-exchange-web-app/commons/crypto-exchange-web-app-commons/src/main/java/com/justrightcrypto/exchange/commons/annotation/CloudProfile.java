/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Crypto Exchange App.
 *
 * Crypto Exchange App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.justrightcrypto.exchange.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * An annotation that is used to capture the profiles where cloud resources are created (e.g. RDS, Route53, etc.). This
 * is typically done in QA, Production profiles. As a result, this approach allows a centralized way of creating
 * resources (e.g. cloud resources) only in the context of specific profiles.
 *
 * @author Jagannath Mishra
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Profile(value = {"dev", "qa", "production"})
public @interface CloudProfile {
}