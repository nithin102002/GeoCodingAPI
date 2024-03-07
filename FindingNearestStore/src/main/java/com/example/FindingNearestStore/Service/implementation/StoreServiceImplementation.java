package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.FindStoreRequestDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Model.CompanyStoreViews;
import com.example.FindingNearestStore.Model.Store;
import com.example.FindingNearestStore.Repository.CompanyRepository;
import com.example.FindingNearestStore.Repository.CompanyStoreViewRepository;
import com.example.FindingNearestStore.Repository.StoreRepository;
import com.example.FindingNearestStore.Service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StoreServiceImplementation implements StoreService {

    Log LOG = LogFactory.getLog(StoreService.class);
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CompanyStoreViewRepository companyStoreViewRepository;
    @Override
    public Store addStoreDetails(StoreDTO storeDTO) throws BadRequestException {


            Optional<Company> company= companyRepository.findById(storeDTO.getCompanyId());
            System.out.println(storeDTO);
            Store savedStoreDetails = null;
            if(company.isPresent()){
//                Store store= Store.builder().storeName(storeDTO.getStoreName()).contactNumber(storeDTO.getContactNumber()).companyId(company.get()).latitude(storeDTO.getLatitude()).longitude(storeDTO.getLongitude()).build();

                Store store= modelMapper.map(storeDTO,Store.class);
                savedStoreDetails= storeRepository.save(store);
            }
            else {
                throw new BadRequestException("You have Entered a wrong Company Id");
            }
            return  savedStoreDetails;

    }

    @Override
    public List<CompanyStoreViews>findStore(FindStoreRequestDTO findStoreRequestDTO) {
        List<CompanyStoreViews> stores=companyStoreViewRepository.findStore(findStoreRequestDTO.getLatitude(),findStoreRequestDTO.getLongitude(), findStoreRequestDTO.getDistance(), findStoreRequestDTO.getCompanyId());
        LOG.info("storelist:"+stores);
        return stores;
    }

    @Override
    public List<CompanyStoreViews> findStores(String companyName) {
       List<CompanyStoreViews> store = companyStoreViewRepository.findStores(companyName);
       return  store;
    }


}
