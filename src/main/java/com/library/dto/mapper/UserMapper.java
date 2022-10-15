package com.library.dto.mapper;

import com.library.domain.User;
import com.library.dto.response.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse userToUserRegisterResponse(User user);

}
