/*
 * Copyright (c) 2020-2021 justrightcrypto.com. All Rights Reserved. This software \n * the confidential and proprietary information of Innominds inc. You shall not \n * such Confidential Information and shall use it only in accordance with the \n *
 */
package com.justrightcrypto.exchange.features.platform.data.model.experience.userroles;

import com.justrightcrypto.exchange.features.platform.data.model.experience.roles.UpdateRolesRequest;
import com.justrightcrypto.exchange.features.platform.data.model.experience.users.UpdateUsersRequest;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Jagannath Mishra
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateUserRolesRequest {
    /** Reference to the id. */
    @NotNull(message = "user.roles.id.not.null.message")
    private Long id;

    /** Reference to the user_id. */
    private Collection<UpdateUsersRequest> userId;

    /** Reference to the role_id. */
    private Collection<UpdateRolesRequest> roleId;
}
