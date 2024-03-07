package com.example.FindingNearestStore.DTO;

import com.example.FindingNearestStore.Model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTokenDTO {

    private String Token;
    private Subscription subscription;
}
