package com.example.FindingNearestStore.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO extends Address {

    @NotNull(message = "company Name must not be empty")
    @Size(max = 15,message = "company name should be exactly 15 character")
    private String companyName;
    @NotNull(message = "Company descryption must not be empty")@Size(min = 10,max = 150,message = "descryption length must be greater than 10 and less than150")
    private String descryption;
    @NotNull(message = "Company contact Number is essential") @Size(min = 10,max = 10,message = "contact number must be exactly 10 digts")
    private String contactNumber;
    @NotNull(message = "company emailid is esential") @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Emailid pattern must be correct")
    private String emailId;
    @NotNull(message = "GST Id must not be empty")@Size(min = 15,max = 15,message = "Company GST Id must be exactly 15 ")
    private String gstNumber;
}
