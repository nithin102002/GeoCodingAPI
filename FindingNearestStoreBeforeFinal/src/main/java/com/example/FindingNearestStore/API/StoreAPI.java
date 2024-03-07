package com.example.FindingNearestStore.API;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping(value = "${store}")
public interface StoreAPI {

    @PostMapping(value = "${add}")
    public ResponseEntity<ResponseDTO>addStoreDetails(@RequestBody StoreDTO storeDTO);

    @GetMapping(value = "${find}")
    public ResponseEntity<ResponseDTO> findStore(@RequestParam BigDecimal latitude,BigDecimal longitude);
}
