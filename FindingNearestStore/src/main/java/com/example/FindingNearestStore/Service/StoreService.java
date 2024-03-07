package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.FindStoreRequestDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Model.CompanyStoreViews;
import com.example.FindingNearestStore.Model.Store;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreService {

    public Store addStoreDetails(StoreDTO storeDTO) throws BadRequestException;

    public List<CompanyStoreViews> findStore(FindStoreRequestDTO findStoreRequestDTO);
    public List<CompanyStoreViews> findStores(String companyName);
}
