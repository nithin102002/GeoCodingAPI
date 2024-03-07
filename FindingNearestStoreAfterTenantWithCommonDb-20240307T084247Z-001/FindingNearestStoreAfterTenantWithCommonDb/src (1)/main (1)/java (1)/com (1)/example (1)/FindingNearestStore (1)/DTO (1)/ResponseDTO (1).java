package com.example.FindingNearestStore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private HttpStatus status;
    private String message;
    private Object data;
}
