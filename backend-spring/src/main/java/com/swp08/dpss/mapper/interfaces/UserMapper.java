package com.swp08.dpss.mapper.interfaces;

import com.swp08.dpss.dto.requests.AdminUserCreationRequest;
import com.swp08.dpss.dto.requests.UserCreationRequest;
import com.swp08.dpss.dto.responses.UserResponse;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.enums.Genders;
import com.swp08.dpss.enums.User_Status;
import com.swp08.dpss.enums.Roles;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "gender", expression = "java(request.getGender() == null ? Genders.PREFER_NOT_TO_SAY : request.getGender())")
    @Mapping(target = "dateOfBirth", expression = "java(request.getDateOfBirth())")
    @Mapping(target = "guardians", ignore = true) // Handled in service
    @Mapping(target = "role", expression = "java(com.swp08.dpss.enums.Roles.MEMBER)")
    @Mapping(target = "status", expression = "java(com.swp08.dpss.enums.User_Status.PENDING)")
    User toEntity(UserCreationRequest request, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "gender", expression = "java(request.getGender() == null ? com.swp08.dpss.enums.Genders.PREFER_NOT_TO_SAY : request.getGender())")
    @Mapping(target = "status", expression = "java(request.getStatus() == null ? com.swp08.dpss.enums.User_Status.VERIFIED : request.getStatus())")
    @Mapping(target = "guardians", ignore = true) // Handled in service
    User toEntity(AdminUserCreationRequest request, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "userId", expression = "java(user.getId())")
    @Mapping(target = "guardianIds", expression = "java(user.getGuardians().stream().map(com.swp08.dpss.entity.Guardian::getGuardianId).collect(java.util.stream.Collectors.toList()))")
    UserResponse toResponse(User user);

    List<UserResponse> toUserResponseList(List<User> userList);
}
