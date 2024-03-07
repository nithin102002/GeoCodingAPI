package com.example.FindingNearestStore.Filter;

import com.example.FindingNearestStore.Service.SubscriptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

@Autowired
    SubscriptionService subscriptionService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/company/login") || request.getRequestURI().equals("/company/add")) {

            filterChain.doFilter(request, response);
        } else {
            String requestApiKey = request.getHeader("API Key");
            String privateKey = System.getProperty("PRIVATE_KEY");

            //converting the private key to string
            byte[] privateKeyByte = Base64.getDecoder().decode(privateKey);
            byte[] requestApiKeyByte = Base64.getDecoder().decode(requestApiKey);
            PrivateKey retrivedPrivateKey;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                retrivedPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            String apikey = "";
            try {
                Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, retrivedPrivateKey);
                cipher.update(requestApiKeyByte);
                byte[] cipherText = cipher.doFinal();
                apikey = Base64.getEncoder().encodeToString(cipherText);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            }

            String subscriptionId = apikey.split("_")[0];
            String expiry = apikey.split("_")[1];
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date expiryDate;
            try {
                expiryDate = format.parse(expiry);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (expiryDate.after(new Date())) {
                throw new BadRequestException("Expiry Key Expired");
            } else {
                subscriptionService.incrementRequestCountInRedis(subscriptionId);
            }
        }
    }

}
