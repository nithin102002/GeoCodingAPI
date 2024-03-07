package com.example.FindingNearestStore.Controller;

import com.example.FindingNearestStore.API.PlanAPI;
import com.example.FindingNearestStore.DTO.PlanDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.Model.Plan;
import com.example.FindingNearestStore.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanController implements PlanAPI {
    @Autowired
    PlanService planService;
    @Override
    public ResponseEntity<ResponseDTO> addPlan(PlanDTO planDTO) {
        Plan plan= planService.addPlan(planDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Adding Plans",plan));
    }
}
