package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.SubscriptionDTO;
import com.example.FindingNearestStore.Model.Plan;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Repository.PlanRepository;
import com.example.FindingNearestStore.Repository.SubscriptionRepository;
import com.example.FindingNearestStore.Service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
@Slf4j
@Service
public class SubscriptionServiceImplemntation implements SubscriptionService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription subscribe(SubscriptionDTO subscriptionDTO) throws BadRequestException {

        Subscription  subscription= new Subscription();
        subscription.setCompanyId(subscriptionDTO.getCompanyId());
        subscription.setPlanId(subscriptionDTO.getPlanId());
        Optional<Plan> plan= planRepository.findById(subscription.getPlanId());
        if(plan.isEmpty()){
            throw new BadRequestException("plan id is not valid");
        }
        log.info("plan object",plan.get());
        System.out.println("dhdjud");
        subscription.setCreatedDate(new Date());
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, plan.get().getExpriyDays());
        subscription.setExpiryDate(cal.getTime());
         return subscriptionRepository.save(subscription);
    }
}
