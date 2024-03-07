package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.CompanyDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Repository.CompanyRepository;
import com.example.FindingNearestStore.Service.CompanyService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired ModelMapper modelMapper;
    @Override
    public Company addCompany(CompanyDTO companyDTO)  {

//        Company company= Company.builder().companyName(companyDTO.getCompanyName()).build();
        Company company= modelMapper.map(companyDTO,Company.class);
       return companyRepository.save(company);


    }
}
