package com.example.FindingNearestStore.Controller;


import com.example.FindingNearestStore.API.StoreAPI;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class StoreController implements StoreAPI {

    @Autowired
    StoreService storeService;
    @Override
    public ResponseEntity<ResponseDTO> addStoreDetails(StoreDTO storeDTO) {
        return storeService.addStoreDetails(storeDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO> findStore(BigDecimal latitude, BigDecimal longitude) {
        return storeService.findStore(latitude,longitude);
    }
}
