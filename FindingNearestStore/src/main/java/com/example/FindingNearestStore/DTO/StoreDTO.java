package com.example.FindingNearestStore.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO extends Address {
    @NotNull(message = "store Name must not be empty")
    @Size(max = 15,message = "Store name length must be less than 10")
    private String storeName;
    @NotNull(message = "Company contact Number is essential") @Size(min = 10,max = 10,message = "contact number must be exactly 10 digts")
    private String contactNumber;
    @NotNull(message = "Company Id must not be empty")
    private String companyId;
    @NotNull(message = "latitude feild must not be empty")  @Range(min = -90,max = 90,message = "latitude range starts from - 90 to 90 degree")
    private BigDecimal latitude;
    @NotNull(message = "longitude field must not be empty") @Range(min=-180,max=180,message = "Longitude Starts from -180 to 180 degree")
    private BigDecimal longitude;

}
