package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.FindStoreRequestDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import com.example.FindingNearestStore.Model.CompanyStoreViews;
import com.example.FindingNearestStore.Model.Store;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public interface StoreService {

    public Store addStoreDetails(StoreDTO storeDTO) throws BadRequestException;

    public List<CompanyStoreViews> findStore(FindStoreRequestDTO findStoreRequestDTO);
//    public List<CompanyStoreViews> findStores(String companyName);

    public byte[] getApiKey(HttpServletRequest httpServletRequest) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException;
}
