package com.viratcodes.projects.BuildMate.mapper;

import com.viratcodes.projects.BuildMate.dto.auth.SignupRequest;
import com.viratcodes.projects.BuildMate.dto.auth.UserProfileResponse;
import com.viratcodes.projects.BuildMate.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);

}
