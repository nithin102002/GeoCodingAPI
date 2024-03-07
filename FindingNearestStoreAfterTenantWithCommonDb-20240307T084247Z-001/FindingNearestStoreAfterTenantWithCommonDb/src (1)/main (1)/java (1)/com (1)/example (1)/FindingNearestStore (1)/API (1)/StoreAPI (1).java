package com.example.FindingNearestStore.API;

import com.example.FindingNearestStore.DTO.FindStoreRequestDTO;
import com.example.FindingNearestStore.DTO.ResponseDTO;
import com.example.FindingNearestStore.DTO.StoreDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequestMapping(value = "${store}")
public interface StoreAPI {

    @PostMapping(value = "${add}")
    public ResponseEntity<ResponseDTO>addStoreDetails(@Valid @RequestBody StoreDTO storeDTO) throws BadRequestException;

    @GetMapping(value = "${find}")
    public ResponseEntity<ResponseDTO> findStore(@Valid @RequestBody FindStoreRequestDTO findStoreRequestDTO);

//    @GetMapping(value = "/getstore")
//    public ResponseEntity<ResponseDTO> findStores(@RequestParam(value = "companyId") String companyName);

    @GetMapping("{getApiKey}")
    public ResponseEntity<String> getApiKey(HttpServletRequest httpServletRequest) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException;
}
