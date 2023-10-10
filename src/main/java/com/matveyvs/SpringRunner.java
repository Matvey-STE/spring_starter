package com.matveyvs;

import com.matveyvs.config.ApplicationConfiguration;
import com.matveyvs.service.CompanyService;
import com.matveyvs.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        var userService = context.getBean(UserService.class);
        var userDto = userService.findById(1l);
        System.out.println(userDto);

        var companyService = context.getBean(CompanyService.class);
        var companyDto = companyService.findById(1);
        System.out.println(companyDto);
    }
}
