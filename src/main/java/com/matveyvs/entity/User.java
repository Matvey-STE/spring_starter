package com.matveyvs.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Builder
public class User {
    private Long id;
    private Timestamp birthDate;
    private String firstName;
    private String lastName;
    private String role;
    private String userName;
    private Company company;
}
