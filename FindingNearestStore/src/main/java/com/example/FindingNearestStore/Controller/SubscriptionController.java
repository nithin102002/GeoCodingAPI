package com.example.FindingNearestStore.Controller;

import com.example.FindingNearestStore.API.SubscriptionAPI;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.SubscriptionDTO;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Service.SubscriptionService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController implements SubscriptionAPI {
    @Autowired
    SubscriptionService subscriptionService;
    @Override
    public ResponseEntity<ResponseDTO> subscribe(SubscriptionDTO subscriptionDTO) throws BadRequestException {
        Subscription subscription= subscriptionService.subscribe(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Getting Subscription",subscription));
    }
}
