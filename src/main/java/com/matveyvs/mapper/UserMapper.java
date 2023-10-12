package com.matveyvs.mapper;

import com.matveyvs.dto.CreateUserDto;
import com.matveyvs.dto.UserDto;
import com.matveyvs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    CompanyMapper companyMapper;
    public UserDto mapFrom(User object) {
        return new UserDto(object.getId(),
                object.getBirthDate(),
                object.getFirstName(),
                object.getLastName(),
                object.getRole(),
                object.getUserName(),
                companyMapper.mapFrom(object.getCompany()));
    }
    public User mapFrom (UserDto object){
        return User.builder()
                .id(object.id())
                .birthDate(object.birthDate())
                .firstName(object.firstName())
                .lastName(object.lastName())
                .role(object.role())
                .userName(object.username())
                .company(companyMapper.mapFrom(object.companyDto()))
                .build();
    }

    public User createFrom(CreateUserDto object) {
        return User.builder()
                .birthDate(object.birthDate())
                .firstName(object.firstName())
                .lastName(object.lastName())
                .role(object.role())
                .userName(object.username())
                .company(companyMapper.mapFrom(object.companyDto()))
                .build();
    }
}
