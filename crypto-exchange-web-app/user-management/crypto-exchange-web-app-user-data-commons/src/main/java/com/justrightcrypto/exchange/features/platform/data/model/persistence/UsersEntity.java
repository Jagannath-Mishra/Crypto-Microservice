/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.model.persistence;

import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "users" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UsersEntity extends AbstractIdGeneratedEntity<Long> {

    /** Reference to the email. */
    @Column(name = "email", unique = true)
    private String email;

    /** Reference to the fname. */
    @Column(name = "fname")
    private String fname;

    /** Reference to the lname. */
    @Column(name = "lname")
    private String lname;

    /** Reference to the contact_no. */
    @Column(name = "contact_no", unique = true)
    private Integer contactNo;
}
