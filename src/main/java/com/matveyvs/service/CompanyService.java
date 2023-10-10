package com.matveyvs.service;

import com.matveyvs.database.repository.CompanyRepository;
import com.matveyvs.dto.CompanyDto;
import com.matveyvs.mapper.CompanyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    public Optional<CompanyDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(companyMapper::mapFrom);
    }
}
