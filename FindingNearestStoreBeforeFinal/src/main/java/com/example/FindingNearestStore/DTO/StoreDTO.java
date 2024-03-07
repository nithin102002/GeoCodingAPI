package com.example.FindingNearestStore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
   private String storeName;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
