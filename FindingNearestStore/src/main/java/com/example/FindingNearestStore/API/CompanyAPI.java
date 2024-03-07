package com.example.FindingNearestStore.API;

import com.example.FindingNearestStore.DTO.AuthRequest;
import com.example.FindingNearestStore.DTO.CompanyDTO;
import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "${companyAPI}")
public interface CompanyAPI {

    @PostMapping("${addCompany}")
    public ResponseEntity<ResponseDTO> addCompany(@Valid @RequestBody CompanyDTO companyDTO) ;

    @PostMapping("${companyLogin}")
    public String login(@RequestBody PayLoadRequestDTO payLoadRequestDTO);
}
