package com.matveyvs.mapper;

import com.matveyvs.dto.UserDto;
import com.matveyvs.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public class UserMapper implements Mapper<UserDto, User> {
    public UserDto mapFrom(User object) {
        return new UserDto(object.getId(),
                object.getBirthDate(),
                object.getFirstName(),
                object.getLastName(),
                object.getRole(),
                object.getUserName(),
                object.getCompany());
    }
}