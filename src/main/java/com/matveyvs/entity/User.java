package com.matveyvs.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class User {
    private Long id;
    private Timestamp birthDate;
    private String firstName;
    private String lastName;
    private Role role;
    private String userName;
    private Company company;
}
