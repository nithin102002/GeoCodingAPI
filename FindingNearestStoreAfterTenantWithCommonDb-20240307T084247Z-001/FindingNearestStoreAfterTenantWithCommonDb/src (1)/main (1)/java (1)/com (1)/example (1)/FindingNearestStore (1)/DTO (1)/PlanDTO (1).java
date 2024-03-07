package com.example.FindingNearestStore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {

    private String planType;
    private String planDescryption;
    private long numberOfRequest;
    private int expriyDays;
}
