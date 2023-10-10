package com.matveyvs.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Company {
    private Integer id;
    private String name;
}
