package com.matveyvs.dto;

import com.matveyvs.entity.Company;

import java.sql.Timestamp;

public record UserDto(Long id, Timestamp birthDate, String firstName,
                      String lastName, String role, String username,
                      Company company) {
}
