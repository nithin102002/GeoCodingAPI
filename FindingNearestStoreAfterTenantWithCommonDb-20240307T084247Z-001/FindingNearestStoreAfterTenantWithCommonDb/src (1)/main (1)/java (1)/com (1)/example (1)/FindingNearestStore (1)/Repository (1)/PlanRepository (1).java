package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan,String> {


}
