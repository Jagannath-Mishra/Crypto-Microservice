/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.model.persistence;

import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "roles" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class RolesEntity extends AbstractIdGeneratedEntity<Long> {

    /** Reference to the role_name. */
    @Column(name = "role_name")
    private String roleName;

    /** Reference to the role_desc. */
    @Column(name = "role_desc")
    private String roleDesc;

    /** Reference to the created_dt. */
    @Column(name = "created_dt")
    private Date createdDt;
}
