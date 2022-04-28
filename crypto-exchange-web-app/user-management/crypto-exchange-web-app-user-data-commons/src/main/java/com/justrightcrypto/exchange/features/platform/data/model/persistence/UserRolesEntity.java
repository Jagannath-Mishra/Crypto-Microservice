/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.model.persistence;

import com.justrightcrypto.exchange.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "user_roles" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_roles")
@Entity
public class UserRolesEntity extends AbstractIdGeneratedEntity<Long> {

    /** Reference to the user_id. */
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "user_roles_id", referencedColumnName = "id")
    private Collection<UsersEntity> userId;

    /** Reference to the role_id. */
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "user_roles_id", referencedColumnName = "id")
    private Collection<RolesEntity> roleId;

    /**
     * This entity method is used to add an existing Users in the system. {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity} into the
     * system.
     *
     * @param users containing the details required to create an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}.
     */
    public void addUsers(final UsersEntity users) {
        if (Objects.nonNull(users)) {
            this.userId.add(users);
        }
    }

    /**
     * This entity method is used to delete an existing Users in the system. instance of {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}}.
     *
     * @param usersId Unique identifier of Users.
     */
    public void deleteUsersById(final Long usersId) {

        final Optional<UsersEntity> matchingRecord = findUsersById(usersId);
        if (matchingRecord.isPresent()) {
            getUserId().remove(matchingRecord.get());
        }
    }

    /**
     * This method is used to find an existing Users in the system. instance of {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}}.
     *
     * @param usersId Unique identifier of Users.
     * @return Return an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}.
     */
    public Optional<UsersEntity> findUsersById(final Long usersId) {

        if (Objects.isNull(userId) || userId.isEmpty()) {
            return Optional.empty();
        }

        return userId.stream().filter(users -> users.getId().equals(usersId)).findFirst();
    }

    /**
     * This entity method is used to retrieve the latest Users of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}.
     *
     * @return Return an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.UsersEntity}.
     */
    public UsersEntity getLatestUsers() {
        final List<UsersEntity> userId = (List<UsersEntity>) this.userId;
        return userId.get(userId.size() - 1);
    }

    /**
     * This entity method is used to add an existing Roles in the system. {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity} into the
     * system.
     *
     * @param roles containing the details required to create an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}.
     */
    public void addRoles(final RolesEntity roles) {
        if (Objects.nonNull(roles)) {
            this.roleId.add(roles);
        }
    }

    /**
     * This entity method is used to delete an existing Roles in the system. instance of {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}}.
     *
     * @param rolesId Unique identifier of Roles.
     */
    public void deleteRolesById(final Long rolesId) {

        final Optional<RolesEntity> matchingRecord = findRolesById(rolesId);
        if (matchingRecord.isPresent()) {
            getRoleId().remove(matchingRecord.get());
        }
    }

    /**
     * This method is used to find an existing Roles in the system. instance of {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}}.
     *
     * @param rolesId Unique identifier of Roles.
     * @return Return an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}.
     */
    public Optional<RolesEntity> findRolesById(final Long rolesId) {

        if (Objects.isNull(roleId) || roleId.isEmpty()) {
            return Optional.empty();
        }

        return roleId.stream().filter(roles -> roles.getId().equals(rolesId)).findFirst();
    }

    /**
     * This entity method is used to retrieve the latest Roles of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}.
     *
     * @return Return an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity}.
     */
    public RolesEntity getLatestRoles() {
        final List<RolesEntity> roleId = (List<RolesEntity>) this.roleId;
        return roleId.get(roleId.size() - 1);
    }
}
