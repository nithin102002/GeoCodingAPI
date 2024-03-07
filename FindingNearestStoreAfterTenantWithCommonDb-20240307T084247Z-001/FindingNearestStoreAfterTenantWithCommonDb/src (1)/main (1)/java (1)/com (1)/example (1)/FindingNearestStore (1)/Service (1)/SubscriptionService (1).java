package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.SubscriptionDTO;
import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import com.example.FindingNearestStore.Model.Subscription;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {

    public Subscription subscribe(SubscriptionDTO subscriptionDTO) throws BadRequestException;

    public Long incrementRequestCountInRedis(String subscriptionId);

    public Plan_subscription_company_view getPlanDetailsByCompanyId(String comapnyId);

    public Boolean isSubscriptionValid(Plan_subscription_company_view planView);

//    public Boolean isRequestLimitExceeded(Plan_subscription_company_view planView,String subscriptionId);

    }
