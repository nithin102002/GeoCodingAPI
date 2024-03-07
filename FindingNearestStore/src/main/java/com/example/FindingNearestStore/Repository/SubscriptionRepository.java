package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,String> {



//
    Subscription findBycompanyIdAndExpiryDateGreaterThan(String companyId, Date date);

//    Subscription findBycompanyId(String companyId);
//
//    Subscription findBycompanyIdAndexpiryDateGreaterThan(String comapnyId, Date date);
}
