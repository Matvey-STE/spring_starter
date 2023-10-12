package com.matveyvs;

import com.matveyvs.config.ApplicationConfiguration;
import com.matveyvs.dto.CreateCompanyDto;
import com.matveyvs.dto.CreateUserDto;
import com.matveyvs.dto.UserDto;
import com.matveyvs.entity.Role;
import com.matveyvs.service.CompanyService;
import com.matveyvs.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SpringRunner {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        var companyService = context.getBean(CompanyService.class);

        //First step CREATE
        var createCompanyDto = new CreateCompanyDto("TestCompany");
        var saveCompany = companyService.save(createCompanyDto);

        CreateUserDto createUserDto = new CreateUserDto(Timestamp.valueOf(LocalDateTime.now()),
                "Oleg",
                "Gorov",
                Role.ADMIN,
                "oleg_hgorv",
                companyService.findById(saveCompany.get().id()).get());

        var userService = context.getBean(UserService.class);
        var saveUser = userService.save(createUserDto);

        //second step READ
        userService.findById(saveUser.get().id());

        //third step UPDATE
        UserDto userDto = new UserDto(saveUser.get().id(), Timestamp.valueOf(LocalDateTime.now()),
                "updateTest",
                "updateLastName",
                Role.USER,
                "usernameTest",
                saveCompany.get());
        userService.update(userDto);

        //fourth step DELETE
        userService.delete(saveUser.get().id());

        companyService.delete(saveCompany.get().id());
    }
}
