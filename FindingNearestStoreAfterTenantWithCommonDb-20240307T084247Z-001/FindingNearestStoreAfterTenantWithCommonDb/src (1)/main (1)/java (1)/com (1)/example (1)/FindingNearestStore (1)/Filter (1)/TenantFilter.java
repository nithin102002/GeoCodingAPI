package com.example.FindingNearestStore.Filter;

import com.example.FindingNearestStore.DataSource.TenantContext;
import com.example.FindingNearestStore.Model.Company;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j

public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);


//        System.out.println(request.toString());
        log.info("Inside tennant filter");
        if( request.getRequestURI().equals("/company/add")) {
            log.info("inside if condition");
            filterChain.doFilter(request, response);
//        } else if (request.getRequestURI().equals("/company/login")) {
//            filterChain.doFilter(contentCachingRequestWrapper,response);
//
//            String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
//            System.out.println("hi        "+requestBody);
//
//
//
//
//
//        } else {
        } else{
            HttpServletRequest req = request;
            String tenantId = req.getHeader("TenantID");
            TenantContext.setCurrentTenant(tenantId);
            System.out.println(TenantContext.getCurrentTenant().toString());


            try {
                filterChain.doFilter(request, response);
            } finally {
                TenantContext.setCurrentTenant("");
            }
        }
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
