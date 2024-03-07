package com.example.FindingNearestStore.API;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.SubscriptionDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "${subscriptionAPI}")
public interface SubscriptionAPI {
    @PostMapping("${addSubscription}")
    public ResponseEntity<ResponseDTO> subscribe(@RequestBody SubscriptionDTO subscriptionDTO) throws BadRequestException;
}
