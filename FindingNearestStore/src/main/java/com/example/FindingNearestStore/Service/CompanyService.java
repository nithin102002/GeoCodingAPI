package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.CompanyDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Company;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    Company addCompany(CompanyDTO companyDTO);
}
