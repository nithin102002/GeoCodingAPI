package com.example.FindingNearestStore.Controller;


import com.example.FindingNearestStore.API.StoreAPI;
import com.example.FindingNearestStore.DTO.FindStoreRequestDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Model.CompanyStoreViews;
import com.example.FindingNearestStore.Model.Store;
import com.example.FindingNearestStore.Service.StoreService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController implements StoreAPI {

    @Autowired
    StoreService storeService;
    @Override
    public ResponseEntity<ResponseDTO> addStoreDetails(StoreDTO storeDTO) throws BadRequestException {
        Store store= storeService.addStoreDetails(storeDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Adding Store Details",store));
    }

    @Override
    public ResponseEntity<ResponseDTO> findStore(@Valid @RequestBody FindStoreRequestDTO findStoreRequestDTO) {
     List<CompanyStoreViews> stores=storeService.findStore(findStoreRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"Finding Nearest Store",stores)) ;

    }

    @Override
    public ResponseEntity<ResponseDTO> findStores(String companyName) {
        List<CompanyStoreViews> store = storeService.findStores(companyName);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"ssss",store));
    }


}
