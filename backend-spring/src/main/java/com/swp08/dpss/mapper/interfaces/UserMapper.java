package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.requests.client.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.client.UpdateUserRequest;
import com.swp08.dpss.dto.requests.client.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.client.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "gender", expression = "java(request.getGender() == null ? Genders.PREFER_NOT_TO_SAY : request.getGender())")
    @Mapping(target = "guardians", ignore = true) // Handled in service
    @Mapping(target = "role", expression = "java(com.swp08.dpss.enums.Roles.MEMBER)")
    @Mapping(target = "status", expression = "java(com.swp08.dpss.enums.User_Status.PENDING)")
    @Mapping(target = "answers" , expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "courseEnrollments" , expression = "java(java.util.Collections.emptyList())")
    User toEntity(UserCreationRequest request, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "gender", expression = "java(request.getGender() == null ? com.swp08.dpss.enums.Genders.PREFER_NOT_TO_SAY : request.getGender())")
    @Mapping(target = "guardians", ignore = true) // Handled in service
    @Mapping(target = "status", expression = "java(com.swp08.dpss.enums.User_Status.VERIFIED)")
    User toEntity(AdminUserCreationRequest request, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "userId", expression = "java(user.getId())")
    @Mapping(target = "guardianIds", expression = "java(user.getGuardians().stream().map(com.swp08.dpss.entity.client.Guardian::getGuardianId).collect(java.util.stream.Collectors.toList()))")
    UserResponse toResponse(User user);

    UserResponse toResponse(UpdateUserRequest request);

    List<UserResponse> toUserResponseList(List<User> userList);
}
