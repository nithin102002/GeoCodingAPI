package com.example.FindingNearestStore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayLoadRequestDTO {

    public String issuedAt;
    public String expiration;
    public String companyName;
    private String password;
    public String companyId;

}
