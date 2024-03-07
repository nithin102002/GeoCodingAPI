package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.Store;
import org.hibernate.mapping.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,String> {

}
