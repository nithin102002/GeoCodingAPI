package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Plan_subscription_company_viewRepository extends JpaRepository<Plan_subscription_company_view,String> {

    Plan_subscription_company_view findByCompanyId(String comapnyId);


    Optional<Plan_subscription_company_view> findBycompanyId(String comapnyId);

    Plan_subscription_company_view findBysubscriptionId(String subscriptionId);
}
