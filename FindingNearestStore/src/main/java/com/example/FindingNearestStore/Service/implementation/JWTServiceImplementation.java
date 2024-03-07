package com.example.FindingNearestStore.Service.implementation;

import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
import com.example.FindingNearestStore.Model.Company;
import com.example.FindingNearestStore.Model.Plan_subscription_company_view;
import com.example.FindingNearestStore.Model.Subscription;
import com.example.FindingNearestStore.Repository.CompanyRepository;
import com.example.FindingNearestStore.Repository.Plan_subscription_company_viewRepository;
import com.example.FindingNearestStore.Repository.SubscriptionRepository;
import com.example.FindingNearestStore.Service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTServiceImplementation implements JWTService {
     @Autowired
    SubscriptionRepository subscriptionRepository;

     @Autowired
    CompanyRepository companyRepository;

    @Autowired
    Plan_subscription_company_viewRepository planViewRepository;
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

    private Claims extractAllClaims(String token) {
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

    private Plan_subscription_company_view getPlanDetailsByCompanyId(String comapnyId) {

        return planViewRepository.findByCompanyId(comapnyId);
    }

    private Boolean isSubscriptionValid(Plan_subscription_company_view planView) {
        return planView.getExpiryDate().after(new Date());
    }

    private Boolean isRequestLimitExceeded(Plan_subscription_company_view planView) {
        return planView.getPlanType().equalsIgnoreCase("limited")
                && planView.getNumberOfRequest() <= 0;
    }


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
        Optional<Company> company= companyRepository.findBycompanyName(payLoadRequestDTO.getCompanyName());
        Subscription subscriptions=subscriptionRepository. findBycompanyIdAndExpiryDateGreaterThan(company.get().getComapnyId(),new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(payLoadRequestDTO.companyName)
                .claim("companyId",company.get().getComapnyId())
                .claim("subscriptionId",subscriptions.getSubscriptionId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

        public Boolean validateToken(String token, UserDetails userDetails,PayLoadRequestDTO payLoadRequestDTO) {
        final String username = extractUsername(token);
            Optional<Company> company= companyRepository.findBycompanyName(username);
            if(company.isPresent()){
                Plan_subscription_company_view plan_subscription_company_view= getPlanDetailsByCompanyId(company.get().getComapnyId());

                if(plan_subscription_company_view != null && isSubscriptionValid(plan_subscription_company_view) ){
                    if(isRequestLimitExceeded(plan_subscription_company_view)){
                        throw new RuntimeException("Request limit exceeded");
                    }
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
