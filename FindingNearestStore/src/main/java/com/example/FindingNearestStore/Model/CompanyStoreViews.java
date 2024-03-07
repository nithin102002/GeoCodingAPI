package com.example.FindingNearestStore.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name="company_store_view")
@Data
public class CompanyStoreViews {

    @Id
    private String storeId;
    private String companyId;
    private String companyName;
    private String storeName;
    private String contactNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
