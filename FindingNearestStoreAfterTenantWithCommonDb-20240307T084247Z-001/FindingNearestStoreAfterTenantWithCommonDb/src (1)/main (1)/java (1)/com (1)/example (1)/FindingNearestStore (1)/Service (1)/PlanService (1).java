package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.PlanDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Plan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PlanService {

    public Plan addPlan(PlanDTO planDTO);
}
