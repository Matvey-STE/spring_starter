package com.matveyvs.dto;

import com.matveyvs.entity.Role;

import java.sql.Timestamp;

public record CreateUserDto(Timestamp birthDate, String firstName,
                            String lastName, Role role, String username,
                            CompanyDto companyDto) {
}
