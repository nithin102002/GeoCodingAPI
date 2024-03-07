package com.example.FindingNearestStore.Model;

import com.example.FindingNearestStore.DTO.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company extends Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String comapnyId;
    private String companyName;
    private String password;
    private String descryption;
    private String contactNumber;
    private String emailId;
    private String gstNumber;




}
