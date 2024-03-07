package com.example.FindingNearestStore.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindStoreRequestDTO {
    @NotNull(message = "latitude feild must not be empty")
    @Range(min = -90,max = 90,message = "latitude range starts from - 90 to 90 degree")
    private BigDecimal latitude;
    @NotNull(message = "longitude field must not be empty")
    @Range(min = -180,max = 180,message = "Longitude Starts from -180 to 180 degree")
    private BigDecimal longitude;
    @NotNull(message = "distance field must not be empty")
    private Integer distance;
    @NotNull(message = "companId must not be empty")
    private String companyId;
}
