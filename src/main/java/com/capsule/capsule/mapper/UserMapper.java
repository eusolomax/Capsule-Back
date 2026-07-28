package com.capsule.capsule.mapper;

import org.mapstruct.Mapper;

import com.capsule.capsule.dtos.request.CreateUserRequest;
import com.capsule.capsule.dtos.response.UserResponse;
import com.capsule.capsule.entities.User;

@Mapper(componentModel = "spring", uses = TrackMapper.class)
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(CreateUserRequest request);

}