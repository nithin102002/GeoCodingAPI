package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
import com.example.FindingNearestStore.DTO.TokenDTO;
import com.example.FindingNearestStore.DataSource.MultitenantDataSource;
import com.example.FindingNearestStore.DataSource.TenantContext;
import com.example.FindingNearestStore.Filter.JWTAuthFilter;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Repository.CompanyRepository;
import com.example.FindingNearestStore.Repository.Plan_subscription_company_viewRepository;
import com.example.FindingNearestStore.Repository.SubscriptionRepository;
import com.example.FindingNearestStore.Service.JWTService;
import com.example.FindingNearestStore.Service.SubscriptionService;
import com.zaxxer.hikari.pool.HikariPool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class JWTServiceImplementation implements JWTService {
     @Autowired
    SubscriptionRepository subscriptionRepository;

     @Autowired
    CompanyRepository companyRepository;

    @Autowired
    Plan_subscription_company_viewRepository planViewRepository;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired MultitenantDataSource multitenantDataSource;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public String extractSubscriptionId(String token) {
        return extractClaim(token,claims -> claims.get("subscriptionId",String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    @Override
//    public String generateToken(PayLoadRequestDTO payLoadRequestDTO) {
//
//        Optional<Company> company= companyRepository.findBycompanyName(payLoadRequestDTO.getCompanyName());
//        if(company.isPresent()){
//            Plan_subscription_company_view plan_subscription_company_view= getPlanDetailsByCompanyId(company.get().getComapnyId());
//
//            if(plan_subscription_company_view != null && isSubscriptionValid(plan_subscription_company_view) ){
//                if(isRequestLimitExceeded(plan_subscription_company_view)){
//                    throw new RuntimeException("Request limit exceeded");
//                }
//                Map<String,Object> claims= new HashMap<>();
//                return createToken(claims,payLoadRequestDTO,company.get(),plan_subscription_company_view);
//            }
//            else {
//                throw new RuntimeException("Plan or Subscription not found or expired");
//            }
//
//
//        }
//        else {
//            throw new RuntimeException("Company not found");
//        }
//
//
//    }


    @Override
    public String generateToken(PayLoadRequestDTO payLoadRequestDTO) {

        Map<String,Object> claims= new HashMap<>();

        return createToken(claims,payLoadRequestDTO);

    }

//    private Plan_subscription_company_view getPlanDetailsByCompanyId(String comapnyId) {
//
//        return planViewRepository.findByCompanyId(comapnyId);
//    }
//
//    private Boolean isSubscriptionValid(Plan_subscription_company_view planView) {
//        return planView.getExpiryDate().after(new Date());
//    }
//
//    private Boolean isRequestLimitExceeded(Plan_subscription_company_view planView) {
//         long count= subscriptionService.incrementRequestCountInRedis()
//        return planView.getPlanType().equalsIgnoreCase("limited")
//                && planView.getNumberOfRequest() <= 0;
//    }


//    private String createToken(Map<String, Object> claims, PayLoadRequestDTO payLoadRequestDTO,Company company,Plan_subscription_company_view plan_subscription_company_view) {
//
//        Subscription subscriptions=subscriptionRepository. findBycompanyIdAndExpiryDateGreaterThan(company.getComapnyId(),new Date());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(payLoadRequestDTO.companyName)
//                .claim("companyId",company.getComapnyId())
//                .claim("subscriptionId",subscriptions.getSubscriptionId())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    private String createToken(Map<String, Object> claims, PayLoadRequestDTO payLoadRequestDTO) {

//        Optional<Company> company= companyRepository.findBycompanyName(payLoadRequestDTO.companyName);


        log.info(TenantContext.getCurrentTenant());
        Optional<Subscription> subscriptions=subscriptionRepository.findBycompanyIdAndExpiryDateGreaterThan(payLoadRequestDTO.getCompanyId(), new Date());
        log.info("subscription:",subscriptions);

        String companyId="";
//     Optional<Subscription>subscriptions= Optional.of(new Subscription());

        String subscriptionId = "";
        if(subscriptions.isEmpty()) {

             companyId=payLoadRequestDTO.getCompanyId();
        }
        else
        {
            companyId=subscriptions.get().getCompanyId();
            subscriptionId= subscriptions.get().getSubscriptionId();
        }
        return
                Jwts.builder()
                .setClaims(claims)
                .setSubject(payLoadRequestDTO.getCompanyName())
                .claim("companyId",companyId)
                .claim("subscriptionId",subscriptionId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

        public Boolean validateToken(String token, UserDetails userDetails,PayLoadRequestDTO payLoadRequestDTO) {
        final String username = extractUsername(token);
        final String subscriptionId= extractSubscriptionId(token);
            Optional<Company> company= companyRepository.findBycompanyName(username);
            Optional<Plan_subscription_company_view> planView= planViewRepository.findBycompanyId(company.get().getCompanyId());
            if(company.isPresent()){
                Plan_subscription_company_view plan_subscription_company_view= subscriptionService.getPlanDetailsByCompanyId(company.get().getCompanyId());

                if(plan_subscription_company_view != null && subscriptionService.isSubscriptionValid(plan_subscription_company_view) || subscriptionId!= null ){
                    Map<String,Object> claims= new HashMap<>();
//                    return createToken(claims,payLoadRequestDTcontentCachingResponseWrapper.copyBodyToResponse();O,company.get(),plan_subscription_company_view);

                    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
                }
                else {
                    throw new RuntimeException("Plan or Subscription not found or expired");
                }


            }
            else {
                throw new RuntimeException("Company not found");
            }


    }



    private Key getSignKey() {

        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
