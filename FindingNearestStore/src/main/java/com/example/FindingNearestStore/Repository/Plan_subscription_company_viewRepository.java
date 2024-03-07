package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Plan_subscription_company_viewRepository extends JpaRepository<Plan_subscription_company_view,String> {

    Plan_subscription_company_view findByCompanyId(String comapnyId);
}
