package com.example.FindingNearestStore.Service;

import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public interface JWTService {

    public String generateToken(PayLoadRequestDTO payLoadRequestDTO);
    public String extractUsername(String token);
    public Date extractExpiration(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public Boolean validateToken(String token, UserDetails userDetails,PayLoadRequestDTO payLoadRequestDTO);

    String extractSubscriptionId(String token);
}
