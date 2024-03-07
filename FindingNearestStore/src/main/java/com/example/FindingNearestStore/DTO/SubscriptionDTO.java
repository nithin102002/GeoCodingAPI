package com.example.FindingNearestStore.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
//   @JsonProperty("planId")
    private String planId;
//    @JsonProperty("companyId")
    private String companyId;

}
