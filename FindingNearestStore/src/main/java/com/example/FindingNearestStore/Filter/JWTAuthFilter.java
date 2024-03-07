package com.example.FindingNearestStore.Filter;

import com.example.FindingNearestStore.Config.CompanyInfoDetailService;
import com.example.FindingNearestStore.DTO.PayLoadRequestDTO;
//import com.example.FindingNearestStore.DTO.ResponseLogDTO;
import com.example.FindingNearestStore.Model.ResponseLog;
import com.example.FindingNearestStore.Repository.ResponseLogRepository;
import com.example.FindingNearestStore.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component

public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    CompanyInfoDetailService companyInfoDetailService;

    @Autowired
    ResponseLogRepository responseLogRepository;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = companyInfoDetailService.loadUserByUsername(username);
            PayLoadRequestDTO payLoadRequestDTO = getPayLoadRequestDTO(request);
            if (jwtService.validateToken(token, userDetails,payLoadRequestDTO)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
//
        String subscriptionId = jwtService.extractSubscriptionId(token);

        //for logging request and response

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), contentCachingRequestWrapper.getCharacterEncoding());
        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), contentCachingResponseWrapper.getCharacterEncoding());

        LOGGER.info(
                "FINISHED PROCESSING : METHOD={}; REQUESTURI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={}; TIM TAKEN={}",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody,
                timeTaken);

//
//        ResponseLogDTO responseLogDTO = new ResponseLogDTO();
//        responseLogDTO.setDate(new Date());
//        responseLogDTO.setRequestMethod(request.getMethod());
//        responseLogDTO.setRequestURI(request.getRequestURI());
//        responseLogDTO.setRequestBody(requestBody);
//        responseLogDTO.setResponseBody(responseBody);
//        responseLogDTO.setStatus(response.getStatus() + "");

        ResponseLog responseLog= new ResponseLog();
        responseLog.setSubscriptionId(subscriptionId);

        responseLog.setDate(new Date());
        responseLog.setRequestMethod(request.getMethod());
        responseLog.setRequestURI(request.getRequestURI());
        responseLog.setRequestBody(requestBody);
        responseLog.setResponseBody(responseBody);
        responseLog.setStatus(response.getStatus() + "");
//        List<ResponseLogDTO> responseLogDTOSList= new ArrayList<>();
//        responseLogDTOSList.add(responseLogDTO);
//        responseLog.setResponse(responseLogDTOSList);

        responseLogRepository.save(responseLog);
        System.out.println("sklskskls");
        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private PayLoadRequestDTO getPayLoadRequestDTO(HttpServletRequest request) {
        String companyName = request.getParameter("companyName");
        PayLoadRequestDTO payLoadRequestDTO = new PayLoadRequestDTO();
        payLoadRequestDTO.setCompanyName(companyName);
        return payLoadRequestDTO;
    }
}

