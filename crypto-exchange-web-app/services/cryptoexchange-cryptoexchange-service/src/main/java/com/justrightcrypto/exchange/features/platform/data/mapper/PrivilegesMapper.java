/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.mapper;

import com.justrightcrypto.exchange.features.platform.data.mapper.decorator.PrivilegesMapperDecorator;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.CreatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.Privileges;
import com.justrightcrypto.exchange.features.platform.data.model.experience.privileges.UpdatePrivilegesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.persistence.PrivilegesEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link PrivilegesEntity to {@link Privileges and vice-versa.
 *
 * @author Jagannath Mishra
 */
@DecoratedWith(value = PrivilegesMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PrivilegesMapper {

    /**
     * This method transforms an instance of type {@link CreatePrivilegesRequest} to an instance of
     * type {@link PrivilegesEntity}.
     *
     * @param source Instance of type {@link CreatePrivilegesRequest} that needs to be transformed
     *     to {@link PrivilegesEntity}.
     * @return Instance of type {@link PrivilegesEntity}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    PrivilegesEntity transform(CreatePrivilegesRequest source);

    /**
     * This method transforms an instance of type {@link PrivilegesEntity} to an instance of type
     * {@link Privileges}.
     *
     * @param source Instance of type {@link PrivilegesEntity} that needs to be transformed to
     *     {@link Privileges}.
     * @return Instance of type {@link Privileges}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    Privileges transform(PrivilegesEntity source);

    /**
     * This method converts / transforms the provided collection of {@link PrivilegesEntity}
     * instances to a collection of instances of type {@link Privileges}.
     *
     * @param source Instance of type {@link PrivilegesEntity} that needs to be transformed to
     *     {@link Privileges}.
     * @return Collection of instance of type {@link Privileges}.
     */
    default Collection<Privileges> transformListTo(Collection<PrivilegesEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdatePrivilegesRequest} to an instance of
     * type {@link PrivilegesEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdatePrivilegesRequest} that needs to be transformed
     *     to {@link PrivilegesEntity}.
     * @param target Instance of type {@link PrivilegesEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    void transform(UpdatePrivilegesRequest source, @MappingTarget PrivilegesEntity target);

    /**
     * This method transforms an instance of type {@link UpdatePrivilegesRequest} to an instance of
     * type {@link PrivilegesEntity}.
     *
     * @param source Instance of type {@link UpdatePrivilegesRequest} that needs to be transformed
     *     to {@link PrivilegesEntity}.
     * @return Instance of type {@link PrivilegesEntity}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    PrivilegesEntity transform(UpdatePrivilegesRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdatePrivilegesRequest}
     * instances to a collection of instances of type {@link PrivilegesEntity}.
     *
     * @param source Instance of type {@link UpdatePrivilegesRequest} that needs to be transformed
     *     to {@link PrivilegesEntity}.
     * @return Instance of type {@link PrivilegesEntity}.
     */
    default Collection<PrivilegesEntity> transformList(Collection<UpdatePrivilegesRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
