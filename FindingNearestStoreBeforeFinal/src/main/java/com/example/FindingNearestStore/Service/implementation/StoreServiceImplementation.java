package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Model.Store;
import com.example.FindingNearestStore.Repository.StoreRepository;
import com.example.FindingNearestStore.Service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class StoreServiceImplementation implements StoreService {

    @Autowired
    StoreRepository storeRepository;
    @Override
    public ResponseEntity<ResponseDTO> addStoreDetails(StoreDTO storeDTO) {
        System.out.println(storeDTO);
        Store store= Store.builder().storeName(storeDTO.getStoreName()).latitude(storeDTO.getLatitude()).longitude(storeDTO.getLongitude()).build();
        Store savedStoreDetails= storeRepository.save(store);
//        System.out.println(savedStoreDetails);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Adding Store Details",savedStoreDetails));
    }

    @Override
    public ResponseEntity<ResponseDTO> findStore(BigDecimal latitude, BigDecimal longitude) {

        List<Store> stores=storeRepository.findStore(latitude,longitude);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Finding Stores",stores));
    }


}
