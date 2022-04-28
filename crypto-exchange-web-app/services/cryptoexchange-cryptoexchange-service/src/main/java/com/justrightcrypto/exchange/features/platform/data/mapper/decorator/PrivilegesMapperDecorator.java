/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.mapper.decorator;

import com.justrightcrypto.exchange.features.platform.data.mapper.PrivilegesMapper;
import com.justrightcrypto.exchange.features.platform.data.mapper.RolesMapper;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.CreatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.Privileges;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.UpdatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity;
import com.justrightcrypto.exchange.features.platform.data.repository.RolesRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link PrivilegesEntity to {@link Privileges and vice-versa.
 *
 * @author Jagannath Mishra
 */
@Slf4j
public abstract class PrivilegesMapperDecorator implements PrivilegesMapper {

    /** Repository implementation of type {@link RolesRepository}. */
    @Autowired private RolesRepository rolesRepository;

    /** Mapper implementation of type {@link RolesMapper}. */
    @Autowired private RolesMapper rolesMapper;

    /** Mapper implementation of type {@link PrivilegesMapper}. */
    @Autowired private PrivilegesMapper privilegesMapper;

    @Override
    public PrivilegesEntity transform(final CreatePrivilegesRequest source) {
        // 1. Transform the CreatePrivilegesRequest to PrivilegesEntity object.
        final PrivilegesEntity privileges = privilegesMapper.transform(source);

        if (Objects.nonNull(source.getRoleId())) {
            privileges.setRoleId(rolesRepository.findByIdOrThrow(source.getRoleId()));
        }

        // Return the transformed object.
        return privileges;
    }

    @Override
    public Privileges transform(final PrivilegesEntity source) {
        // 1. Transform the PrivilegesEntity to Privileges object.
        final Privileges privileges = privilegesMapper.transform(source);

        if (Objects.nonNull(source.getRoleId())) {
            privileges.setRoleId(rolesMapper.transform(source.getRoleId()));
        }

        // Return the transformed object.
        return privileges;
    }

    @Override
    public void transform(
            final UpdatePrivilegesRequest source, final @MappingTarget PrivilegesEntity target) {

        // Transform from source to the target.
        privilegesMapper.transform(source, target);

        if (Objects.nonNull(source.getRoleId())) {
            target.setRoleId(rolesRepository.findByIdOrThrow(source.getRoleId()));
        }
    }

    @Override
    public PrivilegesEntity transform(final UpdatePrivilegesRequest source) {

        // Transform from source to the target.
        final PrivilegesEntity privileges = privilegesMapper.transform(source);

        if (Objects.nonNull(source.getRoleId())) {
            privileges.setRoleId(rolesRepository.findByIdOrThrow(source.getRoleId()));
        }
        // Return the response.
        return privileges;
    }
}
