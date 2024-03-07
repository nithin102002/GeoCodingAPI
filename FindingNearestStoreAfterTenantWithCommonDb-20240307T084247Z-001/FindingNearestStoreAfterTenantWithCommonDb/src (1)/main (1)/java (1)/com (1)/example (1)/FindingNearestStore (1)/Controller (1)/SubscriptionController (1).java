package com.example.FindingNearestStore.Controller;

import com.example.FindingNearestStore.API.SubscriptionAPI;
import com.example.FindingNearestStore.DTO.*;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Service.JWTService;
import com.example.FindingNearestStore.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionAPI {

    private final SubscriptionService subscriptionService;

    private final JWTService jwtService;
    @Override
    public ResponseEntity<ResponseDTO> subscribe(SubscriptionDTO subscriptionDTO) throws BadRequestException {
        Subscription subscription= subscriptionService.subscribe(subscriptionDTO);
        PayLoadRequestDTO payLoadRequestDTO= new PayLoadRequestDTO();
        payLoadRequestDTO.companyId= subscriptionDTO.getCompanyId();
        String token = jwtService.generateToken(payLoadRequestDTO);
        SubscriptionTokenDTO subscriptionTokenDTO= new SubscriptionTokenDTO();
        subscriptionTokenDTO.setToken(token);
        subscriptionTokenDTO.setSubscription(subscription);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Getting Subscription",subscriptionTokenDTO));
    }
}
