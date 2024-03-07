package com.example.FindingNearestStore.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "plan_subscription_company_view")
public class Plan_subscription_company_view {
    @Id
    private String planId;
    private String planType;
    private String planDescryption;
    private long numberOfRequest;
    private int expriyDays;
    private String subscriptionId;
    private String companyId;
    private Date createdDate;
    private Date ExpiryDate;
    private String companyName;
}
