package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.SubscriptionDTO;
import com.example.FindingNearestStore.Model.Plan;
import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Redis.RedisCount;
import com.example.FindingNearestStore.Repository.PlanRepository;
import com.example.FindingNearestStore.Repository.Plan_subscription_company_viewRepository;
import com.example.FindingNearestStore.Repository.SubscriptionRepository;
import com.example.FindingNearestStore.Service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SubscriptionServiceImplemntation implements SubscriptionService {

    @Autowired
    RedisCount redisCount;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    Plan_subscription_company_viewRepository planViewRepository;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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

    public Long incrementRequestCountInRedis(String subscriptionId) {
       Plan_subscription_company_view plan= planViewRepository.findBysubscriptionId(subscriptionId);
        Long count=0L;
        System.out.println("incremented");
       if(plan.getPlanType().equalsIgnoreCase("limitedPerDay")){
           String currentDate= formateDate(new Date());
           String redisKey= generateRedisKeyPerDay(subscriptionId,currentDate);

           if(redisCount.hasKey(redisKey)){
               count=redisCount.incrementRedisKey(redisKey);
           }
           else{
               redisCount.setKey(redisKey,1);
           }
           if (count != null && count > plan.getNumberOfRequest()) {
               log.info("Request limit exceeded. Count: {}", count);
               throw new RuntimeException("Request limit exceeded");
           }

           return count;
       } else if (plan.getPlanType().equalsIgnoreCase("limited")) {

           String redisKey= generateRedisKey(subscriptionId);

           if(redisCount.hasKey(redisKey)){
               count=redisCount.incrementRedisKey(redisKey);
           }


           else{
               redisCount.setKey(redisKey,1);
           }


           if (count != null && count > plan.getNumberOfRequest()) {
               log.info("Request limit exceeded. Count: {}", count);
               throw new RuntimeException("Request limit exceeded");
           }
           return count;
       }
       else{
           count++;

        }
        return count;
    }


    private String generateRedisKeyPerDay(String subscriptionId, String currentDate) {

        return "Request_Count" + subscriptionId +":" + currentDate;

    }

    private String generateRedisKey(String subscriptionId) {

        return "Request_Count" + subscriptionId;

    }

    private String formateDate(Date date) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyy-MM-dd");
        return dateFormat.format(date);

    }
    public Plan_subscription_company_view getPlanDetailsByCompanyId(String comapnyId) {

        return planViewRepository.findByCompanyId(comapnyId);
    }

    public Boolean isSubscriptionValid(Plan_subscription_company_view planView) {
        return planView.getExpiryDate().after(new Date());
    }

//    public Boolean isRequestLimitExceeded(Plan_subscription_company_view planView,String subscriptionId ) {
//      long count= incrementRequestCountInRedis(subscriptionId);
//        System.out.println("count- " + count);
//        return planView.getPlanType().equalsIgnoreCase("limited")
//                && planView.getNumberOfRequest() >=count;
//    }
}
