/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.model.persistence;

import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Implementation that maps the "privileges" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "privileges")
@Entity
public class PrivilegesEntity extends AbstractIdGeneratedEntity<Long> {

    /** Reference to the name. */
    @Column(name = "name")
    private String name;

    /** Reference to the description. */
    @Column(name = "description")
    private String description;

    /** Reference to the role_id. */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RolesEntity roleId;
}
