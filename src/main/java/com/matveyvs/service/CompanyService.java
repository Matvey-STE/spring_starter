package com.matveyvs.service;

import com.matveyvs.database.repository.CompanyRepository;
import com.matveyvs.dto.CompanyDto;
import com.matveyvs.dto.UserDto;
import com.matveyvs.mapper.CompanyMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    public Optional<CompanyDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(companyMapper::mapFrom);
    }
}
