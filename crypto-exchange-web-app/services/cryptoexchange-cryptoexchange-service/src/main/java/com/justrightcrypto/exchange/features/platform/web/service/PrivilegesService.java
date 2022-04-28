/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.web.service;

import com.justrightcrypto.exchange.commons.data.utils.PageUtils;
import com.justrightcrypto.exchange.commons.exception.ServiceException;
import com.justrightcrypto.exchange.commons.instrumentation.Instrument;
import com.justrightcrypto.exchange.error.Errors;
import com.justrightcrypto.exchange.features.platform.data.mapper.PrivilegesMapper;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.CreatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.Privileges;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.UpdatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.RolesEntity;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.UserRolesEntity;
import com.justrightcrypto.exchange.features.platform.data.repository.PrivilegesRepository;
import com.justrightcrypto.exchange.features.platform.data.repository.RolesRepository;
import com.justrightcrypto.exchange.features.platform.data.repository.UserRolesRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link PrivilegesEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@Validated
@Service
public class PrivilegesService {

    /** Repository implementation of type {@link PrivilegesRepository}. */
    private final PrivilegesRepository privilegesRepository;

    /**
     * Mapper implementation of type {@link PrivilegesMapper} to transform between different types.
     */
    private final PrivilegesMapper privilegesMapper;

    /** Repository implementation of type {@link UserRolesRepository}. */
    private final UserRolesRepository userRolesRepository;

    /** Repository implementation of type {@link RolesRepository}. */
    private final RolesRepository rolesRepository;

    /**
     * Constructor.
     *
     * @param privilegesRepository Repository instance of type {@link PrivilegesRepository}.
     * @param privilegesMapper Mapper instance of type {@link PrivilegesMapper}.
     * @param userRolesRepository Repository instance of type {@link UserRolesRepository}.
     * @param rolesRepository Repository instance of type {@link RolesRepository}.
     */
    public PrivilegesService(
            final PrivilegesRepository privilegesRepository,
            final PrivilegesMapper privilegesMapper,
            final UserRolesRepository userRolesRepository,
            final RolesRepository rolesRepository) {
        this.privilegesRepository = privilegesRepository;
        this.privilegesMapper = privilegesMapper;
        this.userRolesRepository = userRolesRepository;
        this.rolesRepository = rolesRepository;
    }

    /**
     * This method attempts to create an instance of type {@link PrivilegesEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     PrivilegesEntity}.
     * @return An experience model of type {@link Privileges} that represents the newly created
     *     entity of type {@link PrivilegesEntity}.
     */
    @Instrument
    @Transactional
    public Privileges createPrivileges(
            final Long userRolesId,
            final Long rolesId,
            @Valid final CreatePrivilegesRequest payload) {
        // 1. Validate the dependencies.

        final UserRolesEntity matchingUserRoles =
                userRolesRepository
                        .findById(userRolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, userRolesId));

        final RolesEntity matchingRoles =
                rolesRepository
                        .findById(rolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, rolesId));

        // 2.Transform the experience model to a persistence model.
        final PrivilegesEntity privilegesEntity = privilegesMapper.transform(payload);

        // 3.Set the  parent persistence model to current persistence model.
        privilegesEntity.setRoleId(matchingRoles);

        // 4. Save the entity.
        final PrivilegesEntity savedInstance = privilegesRepository.save(privilegesEntity);

        // 5. Transform and return.
        return privilegesMapper.transform(savedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link PrivilegesEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdatePrivilegesRequest}.
     *
     * @param privilegesId Unique identifier of Privileges in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Privileges, which needs
     *     to be updated in the system.
     * @return A instance of type {@link Privileges} containing the updated details.
     */
    @Instrument
    @Transactional
    public Privileges updatePrivileges(
            final Long userRolesId,
            final Long rolesId,
            final Long privilegesId,
            @Valid final UpdatePrivilegesRequest payload) {
        // 1. Validate the dependencies.

        final UserRolesEntity matchingUserRoles =
                userRolesRepository
                        .findById(userRolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, userRolesId));

        final RolesEntity matchingRoles =
                rolesRepository
                        .findById(rolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, rolesId));

        final PrivilegesEntity matchingPrivileges =
                privilegesRepository
                        .findById(privilegesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, privilegesId));

        if (!matchingPrivileges.getRoleId().equals(matchingRoles)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform the experience model to a persistence model.
        privilegesMapper.transform(payload, matchingPrivileges);

        // 3. Save the entity.
        final PrivilegesEntity updatedInstance = privilegesRepository.save(matchingPrivileges);

        // 4. Transform and return.
        return privilegesMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link PrivilegesEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param privilegesId Unique identifier of Privileges in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Privileges} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Privileges findPrivileges(
            final Long userRolesId, final Long rolesId, final Long privilegesId) {

        // 1. Validate the dependencies.

        final UserRolesEntity matchingUserRoles =
                userRolesRepository
                        .findById(userRolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, userRolesId));

        final RolesEntity matchingRoles =
                rolesRepository
                        .findById(rolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, rolesId));

        final PrivilegesEntity matchingPrivileges =
                privilegesRepository
                        .findById(privilegesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, privilegesId));

        if (!matchingPrivileges.getRoleId().equals(matchingRoles)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform and return.
        return privilegesMapper.transform(matchingPrivileges);
    }

    /**
     * This method attempts to find instances of type PrivilegesEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Privileges}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Privileges> findAllPrivilegeses(
            final Long userRolesId, final Long rolesId, final Pageable page) {
        // 0. Validate the dependencies.

        final UserRolesEntity matchingUserRoles =
                userRolesRepository
                        .findById(userRolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, userRolesId));

        final RolesEntity matchingRoles =
                rolesRepository
                        .findById(rolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, rolesId));

        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);

        PrivilegesService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<PrivilegesEntity> pageData =
                privilegesRepository.findAllByRolesId(rolesId, pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Privileges> dataToReturn =
                    pageData.getContent().stream()
                            .map(privilegesMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // 4. Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }
    /**
     * This method attempts to delete an existing instance of type {@link PrivilegesEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param privilegesId Unique identifier of Privileges in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type PrivilegesEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Long deletePrivileges(
            final Long userRolesId, final Long rolesId, final Long privilegesId) {
        // 1. Validate the dependencies.

        final UserRolesEntity matchingUserRoles =
                userRolesRepository
                        .findById(userRolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, userRolesId));

        final RolesEntity matchingRoles =
                rolesRepository
                        .findById(rolesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, rolesId));

        final PrivilegesEntity matchingPrivileges =
                privilegesRepository
                        .findById(privilegesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, privilegesId));

        if (!matchingPrivileges.getRoleId().equals(matchingRoles)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2.Delete Persistence Entity
        privilegesRepository.deleteById(privilegesId);

        // 3. Return the deleted identifier
        return privilegesId;
    }
}
