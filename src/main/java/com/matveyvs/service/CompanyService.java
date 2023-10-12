package com.matveyvs.service;

import com.matveyvs.database.repository.CompanyRepository;
import com.matveyvs.dto.CompanyDto;
import com.matveyvs.dto.CreateCompanyDto;
import com.matveyvs.entity.AccessType;
import com.matveyvs.entity.Company;
import com.matveyvs.listener.EntityEventAfter;
import com.matveyvs.listener.EntityEventBefore;
import com.matveyvs.mapper.CompanyMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Optional<CompanyDto> findById(Integer id) {
        applicationEventPublisher.publishEvent(new EntityEventBefore("Find company DTO by ID " + id, AccessType.READ));
        return companyRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEventAfter(entity, AccessType.READ));
                    return companyMapper.mapFrom(entity);
                });
    }

    public Optional<CompanyDto> save(CreateCompanyDto createCompanyDto) {
        applicationEventPublisher.publishEvent(new EntityEventBefore(createCompanyDto, AccessType.CREATE));
        Company save = companyRepository.save(companyMapper.createFrom(createCompanyDto));
        Optional<CompanyDto> companyDto = Optional.ofNullable(companyMapper.mapFrom(save));
        if(companyDto.isPresent()){
            applicationEventPublisher.publishEvent(new EntityEventAfter(companyDto, AccessType.CREATE));
        }
        return companyDto;
    }

    public boolean update(CompanyDto companyDto) {
        applicationEventPublisher.publishEvent(new EntityEventBefore(companyDto, AccessType.UPDATE));
        boolean update = companyRepository.update(companyMapper.mapFrom(companyDto));
        if (update){
            applicationEventPublisher.publishEvent(new EntityEventAfter(companyDto, AccessType.UPDATE));
        }
        return update;
    }

    public boolean delete(Integer id) {
        Optional<Company> byId = companyRepository.findById(id);
        applicationEventPublisher.publishEvent(new EntityEventBefore(byId, AccessType.DELETE));
        boolean delete = companyRepository.delete(id);
        if(delete){
            applicationEventPublisher.publishEvent(new EntityEventAfter(byId, AccessType.DELETE));
        }
        return delete;
    }
}
