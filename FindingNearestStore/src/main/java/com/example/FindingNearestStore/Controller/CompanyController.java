package com.example.FindingNearestStore.Controller;

import com.example.FindingNearestStore.API.CompanyAPI;
import com.example.FindingNearestStore.DTO.AuthRequest;
import com.example.FindingNearestStore.DTO.CompanyDTO;
import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Service.CompanyService;
import com.example.FindingNearestStore.Service.JWTService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController implements CompanyAPI {
@Autowired CompanyService companyService;
@Autowired
    JWTService jwtService;
@Autowired
AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<ResponseDTO> addCompany(CompanyDTO companyDTO)  {
         Company company= companyService.addCompany(companyDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Adding Company Details",company));

    }

    @Override
    public String login(PayLoadRequestDTO payLoadRequestDTO) {

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payLoadRequestDTO.getCompanyName(),payLoadRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("hjhiww");
            return jwtService.generateToken(payLoadRequestDTO);

        }

        else {
            throw new UsernameNotFoundException("invalid");
        }


    }
}
