package com.matveyvs.mapper;

import com.matveyvs.dto.CompanyDto;
import com.matveyvs.dto.CreateCompanyDto;
import com.matveyvs.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDto mapFrom(Company object) {
        return new CompanyDto(object.getId(),
                object.getName());
    }

    public Company mapFrom(CompanyDto object) {
        return Company.builder()
                .id(object.id())
                .name(object.name())
                .build();
    }

    public Company createFrom(CreateCompanyDto object) {
        return Company.builder()
                .name(object.name())
                .build();
    }
}
