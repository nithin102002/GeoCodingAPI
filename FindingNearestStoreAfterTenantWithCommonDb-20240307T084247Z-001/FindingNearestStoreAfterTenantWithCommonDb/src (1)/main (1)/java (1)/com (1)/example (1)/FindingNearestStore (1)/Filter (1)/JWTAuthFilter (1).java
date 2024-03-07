package com.example.FindingNearestStore.Filter;

import com.example.FindingNearestStore.Config.CompanyInfoDetailService;
import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
//import com.example.FindingNearestStore.DTO.ResponseLogDTO;
import com.example.FindingNearestStore.Model.ResponseLog;
import com.example.FindingNearestStore.Redis.RedisCount;
import com.example.FindingNearestStore.Repository.ResponseLogRepository;
import com.example.FindingNearestStore.Service.JWTService;
import com.example.FindingNearestStore.Service.SubscriptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    CompanyInfoDetailService companyInfoDetailService;


    @Autowired
    ResponseLogRepository responseLogRepository;

    @Autowired
    SubscriptionService subscriptionService;

    @Value("${PRIVATE_KEY}")
    private String privateKey;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    //for logging request and response
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthFilter.class);

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
protected boolean shouldNotFilter(HttpServletRequest httpServletRequest){
        String path= httpServletRequest.getRequestURI();
    ArrayList<String> arr= new ArrayList<>();
    arr.add("/company/add");
    arr.add("/company/login");
//    arr.add("/store/findStores");

    return arr.contains(path);
}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                System.out.println("welcome");

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);

        log.info(request.getRequestURL().toString());
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
                String subscriptionId="";
        if (request.getRequestURI().equals("/store/findStores")) {
            String requestApiKey = request.getHeader("Authorization");
            System.out.println(privateKey + "a for apple");

            //converting the private key to string
            byte[] privateKeyByte = Base64.getDecoder().decode(privateKey);
            byte[] requestApiKeyByte = Base64.getDecoder().decode(requestApiKey.getBytes(StandardCharsets.ISO_8859_1));
            PrivateKey retrivedPrivateKey;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(privateKeyByte);
                retrivedPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            String apikey = "";
            byte[] apiKeyInBytes;
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, retrivedPrivateKey);
                cipher.update(requestApiKeyByte);
                byte[] cipherText = cipher.doFinal();
                apikey = new String(cipherText);

                System.out.println(apikey);
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
            System.out.println("ApiKey " + apikey);
             subscriptionId = apikey.split("_")[0];


            String expiry = apikey.split("_")[1];
//            SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            Date expiryDate;
            try {
                expiryDate = sdf.parse(expiry);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (!expiryDate.after(new Date())) {
                throw new BadRequestException("Expiry Key Expired");
            } else {
                subscriptionService.incrementRequestCountInRedis(subscriptionId);


          }
            filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

//            System.out.println("before filter");
//            SecurityContextHolder.getContext().setAuthentication(new TokenAuthentication());
//            log.info(SecurityContextHolder.getContext().getAuthentication().toString());
//            log.info(SecurityContextHolder.getContext().toString());
//            System.out.println("after filter");
//            log.info(SecurityContextHolder.getContext().toString());
//            filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        } else {
            log.info("Hello");
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = companyInfoDetailService.loadUserByUsername(username);
                PayLoadRequestDTO payLoadRequestDTO = getPayLoadRequestDTO(request);
                if (jwtService.validateToken(token, userDetails, payLoadRequestDTO)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    subscriptionId = jwtService.extractSubscriptionId(token);
                }
            }
            filterChain.doFilter(request,response);
        }

        if(request.getRequestURI().equals("/store/findStores")) {

            long startTime = System.currentTimeMillis();

            long timeTaken = System.currentTimeMillis() - startTime;
            String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), contentCachingRequestWrapper.getCharacterEncoding());
            String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), contentCachingResponseWrapper.getCharacterEncoding());

            // Log or print to console for debugging
            System.out.println("Request Body: " + requestBody);
            System.out.println("Response Body: " + responseBody);

            LOGGER.info(
                    "FINISHED PROCESSING : METHOD={}; REQUESTURI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={}; TIM TAKEN={}",
                    request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody,
                    timeTaken);

            ResponseLog responseLog = new ResponseLog();
            responseLog.setSubscriptionId(subscriptionId);
            responseLog.setDate(new Date());
            responseLog.setRequestMethod(request.getMethod());
            responseLog.setRequestURI(request.getRequestURI());
            responseLog.setRequestBody(requestBody);
            responseLog.setResponseBody(responseBody);
            responseLog.setStatus(response.getStatus() + "");

            System.out.println("Response Log: " + responseLog);
            responseLogRepository.save(responseLog);
        }
                 contentCachingResponseWrapper.copyBodyToResponse();





        }

    private PayLoadRequestDTO getPayLoadRequestDTO(HttpServletRequest request) {
        String companyName = request.getParameter("companyName");
        PayLoadRequestDTO payLoadRequestDTO = new PayLoadRequestDTO();
        payLoadRequestDTO.setCompanyName(companyName);
        return payLoadRequestDTO;
    }

}

