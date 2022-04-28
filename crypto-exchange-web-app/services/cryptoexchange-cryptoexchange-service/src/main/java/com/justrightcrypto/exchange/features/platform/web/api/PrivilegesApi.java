/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.web.api;

import com.justrightcrypto.exchange.commons.data.utils.PageUtils;
import com.justrightcrypto.exchange.commons.web.api.AbstractApi;
import com.justrightcrypto.exchange.commons.web.configuration.properties.ApiDocumentationSettings;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.CreatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.Privileges;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.UpdatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.web.service.PrivilegesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@RestController
@RequestMapping(PrivilegesApi.rootEndPoint)
public class PrivilegesApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Privilegeses";

    /** Root end point. */
    public static final String rootEndPoint = "/cryptoexchange-cryptoexchange";

    /** Service implementation of type {@link PrivilegesService}. */
    private final PrivilegesService privilegesService;

    /**
     * Constructor.
     *
     * @param privilegesService Service instance of type {@link PrivilegesService}.
     */
    public PrivilegesApi(final PrivilegesService privilegesService) {
        this.privilegesService = privilegesService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity} into
     * the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Privileges}.
     */
    @Operation(
            method = "createPrivileges",
            summary = "Create a new Privileges.",
            description = "This API is used to create a new Privileges in the system.",
            tags = {PrivilegesApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Privileges in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PostMapping(value = "/userRoleses/{userRolesId}/roleses/{rolesId}/privilegeses")
    public ResponseEntity<Privileges> createPrivileges(
            @PathVariable(name = "userRolesId") final Long userRolesId,
            @PathVariable(name = "rolesId") final Long rolesId,
            @Valid @RequestBody final CreatePrivilegesRequest payload) {
        // Delegate to the service layer.
        final Privileges newInstance =
                privilegesService.createPrivileges(userRolesId, rolesId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity} in
     * the system.
     *
     * @param userRolesId Unique identifier of UserRoles in the system, whose details have to be
     *     retrieved.
     * @param rolesId Unique identifier of Roles in the system, whose details have to be retrieved.
     * @param privilegesId Unique identifier of Privileges in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Privileges, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Privileges}.
     */
    @Operation(
            method = "updatePrivileges",
            summary = "Update an existing Privileges.",
            description = "This API is used to update an existing Privileges in the system.",
            tags = {PrivilegesApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Privileges in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PutMapping(value = "/userRoleses/{userRolesId}/roleses/{rolesId}/privilegeses/{privilegesId}")
    public ResponseEntity<Privileges> updatePrivileges(
            @PathVariable(name = "userRolesId") final Long userRolesId,
            @PathVariable(name = "rolesId") final Long rolesId,
            @PathVariable(name = "privilegesId") final Long privilegesId,
            @Valid @RequestBody final UpdatePrivilegesRequest payload) {
        // Delegate to the service layer.
        final Privileges updatedInstance =
                privilegesService.updatePrivileges(userRolesId, rolesId, privilegesId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity} in
     * the system.
     *
     * @param userRolesId Unique identifier of UserRoles in the system, whose details have to be
     *     retrieved.
     * @param rolesId Unique identifier of Roles in the system, whose details have to be retrieved.
     * @param privilegesId Unique identifier of Privileges in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Privileges}.
     */
    @Operation(
            method = "findPrivileges",
            summary = "Find an existing Privileges.",
            description = "This API is used to find an existing Privileges in the system.",
            tags = {PrivilegesApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Privileges in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @GetMapping(value = "/userRoleses/{userRolesId}/roleses/{rolesId}/privilegeses/{privilegesId}")
    public ResponseEntity<Privileges> findPrivileges(
            @PathVariable(name = "userRolesId") final Long userRolesId,
            @PathVariable(name = "rolesId") final Long rolesId,
            @PathVariable(name = "privilegesId") final Long privilegesId) {
        // Delegate to the service layer.
        final Privileges matchingInstance =
                privilegesService.findPrivileges(userRolesId, rolesId, privilegesId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity} in
     * the system in a paginated manner.
     *
     * @param userRolesId Unique identifier of UserRoles in the system, whose details have to be
     *     retrieved.
     * @param rolesId Unique identifier of Roles in the system, whose details have to be retrieved.
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Privileges based on the provided pagination settings.
     */
    @Operation(
            method = "findAllPrivilegeses",
            summary = "Find all Privilegeses.",
            description = "This API is used to find all Privilegeses in the system.",
            tags = {PrivilegesApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Privilegeses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @GetMapping(value = "/userRoleses/{userRolesId}/roleses/{rolesId}/privilegeses")
    public ResponseEntity<Page<Privileges>> findAllPrivilegeses(
            @PathVariable(name = "userRolesId") final Long userRolesId,
            @PathVariable(name = "rolesId") final Long rolesId,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Privileges> matchingInstances =
                privilegesService.findAllPrivilegeses(userRolesId, rolesId, pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity} in
     * the system.
     *
     * @param userRolesId Unique identifier of UserRoles in the system, whose details have to be
     *     retrieved.
     * @param rolesId Unique identifier of Roles in the system, whose details have to be retrieved.
     * @param privilegesId Unique identifier of Privileges in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link
     *     com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity}
     *     that was deleted from the system.
     */
    @Operation(
            method = "deletePrivileges",
            summary = "Delete an existing Privileges.",
            description = "This API is used to delete an existing Privileges in the system.",
            tags = {PrivilegesApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing Privileges in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @DeleteMapping(
            value = "/userRoleses/{userRolesId}/roleses/{rolesId}/privilegeses/{privilegesId}")
    public ResponseEntity<Long> deletePrivileges(
            @PathVariable(name = "userRolesId") final Long userRolesId,
            @PathVariable(name = "rolesId") final Long rolesId,
            @PathVariable(name = "privilegesId") final Long privilegesId) {
        // Delegate to the service layer.
        final Long deletedInstance =
                privilegesService.deletePrivileges(userRolesId, rolesId, privilegesId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
