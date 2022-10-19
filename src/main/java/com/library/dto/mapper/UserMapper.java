package com.library.dto.mapper;

import com.library.domain.User;
import com.library.dto.UserDTO;
import com.library.dto.response.UserRegisterResponse;
import com.library.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse userToUserRegisterResponse(User user);
    List<UserResponse> map(List<User> user);

    UserResponse userToUserResponse(User user);
    @Mapping(target="roles",ignore=true)
    User userDTOToUser(UserDTO userDTO);

}
