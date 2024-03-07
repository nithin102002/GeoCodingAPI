package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.PlanDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Plan;
import com.example.FindingNearestStore.Repository.PlanRepository;
import com.example.FindingNearestStore.Service.PlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImplementation implements PlanService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PlanRepository planRepository;
    @Override
    public Plan addPlan(PlanDTO planDTO) {
        Plan plan= modelMapper.map(planDTO,Plan.class);
        return planRepository.save(plan);
    }
}
