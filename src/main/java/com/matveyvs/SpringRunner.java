package com.matveyvs;

import com.matveyvs.service.CompanyService;
import com.matveyvs.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("application.xml");

        var userService = context.getBean(UserService.class);
        var userDto = userService.findById(1l);
        System.out.println(userDto);

        var companyService = context.getBean(CompanyService.class);
        var companyDto = companyService.findById(1);
        System.out.println(companyDto);
    }
}
