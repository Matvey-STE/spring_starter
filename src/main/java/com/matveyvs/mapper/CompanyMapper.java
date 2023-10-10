package com.matveyvs.mapper;

import com.matveyvs.dto.CompanyDto;
import com.matveyvs.entity.Company;

public class CompanyMapper implements Mapper<CompanyDto, Company> {
    @Override
    public CompanyDto mapFrom(Company object) {
        return new CompanyDto(object.getId(),
                object.getName());
    }
}
