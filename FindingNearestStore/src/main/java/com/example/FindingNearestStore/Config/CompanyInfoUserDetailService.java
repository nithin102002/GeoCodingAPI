package com.example.FindingNearestStore.Config;

import com.example.FindingNearestStore.Model.Company;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CompanyInfoUserDetailService implements UserDetails {

 private String companyName;
 private String password;
 private List<GrantedAuthority> authorities;

 public CompanyInfoUserDetailService(Company company){

     companyName=company.getCompanyName();
     password=company.getPassword();

 }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return companyName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
