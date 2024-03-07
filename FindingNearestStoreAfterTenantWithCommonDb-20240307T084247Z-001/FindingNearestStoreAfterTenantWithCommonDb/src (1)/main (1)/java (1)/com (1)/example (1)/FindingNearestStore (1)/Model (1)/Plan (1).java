package com.example.FindingNearestStore.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plan  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String planId;
    private String planType;
    private String planDescryption;
    private long numberOfRequest;
    private int expriyDays;
}
