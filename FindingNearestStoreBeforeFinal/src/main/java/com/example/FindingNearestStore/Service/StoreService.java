package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface StoreService {

    public ResponseEntity<ResponseDTO> addStoreDetails(StoreDTO storeDTO);

    public ResponseEntity<ResponseDTO> findStore(BigDecimal latitude, BigDecimal longitude);
}
