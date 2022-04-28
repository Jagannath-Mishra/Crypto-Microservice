/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.repository;

import com.justrightcrypto.exchange.commons.data.jpa.repository.ExtendedJpaRepository;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type
 * "PrivilegesEntity".
 *
 * @author Jagannath Mishra
 */
@Repository
public interface PrivilegesRepository extends ExtendedJpaRepository<PrivilegesEntity, Long> {
    /**
     * This method extracts leaves for the given time period
     * @param rolesId
     *        Unique identifier of the roles.
     * @param pageable
     *        Page settings.
     * @return A page of privileges instances where each element in the page is an instance of type {@link
     *        PrivilegesEntity
     */
    @Query(value = "SELECT entity FROM PrivilegesEntity entity WHERE entity.role_id.id = :rolesId")
    Page<PrivilegesEntity> findAllByRolesId(@Param("rolesId") Long rolesId, Pageable pageable);
}
