package com.example.FindingNearestStore.Model;

import com.example.FindingNearestStore.DTO.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Store extends Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeId;
    private String companyId;
    private String storeName;
    private String contactNumber;
    private BigDecimal  latitude;
    private BigDecimal longitude;

}
