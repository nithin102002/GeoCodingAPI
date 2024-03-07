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

String formula= "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *" +
        " cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude))))";

//@Query("SELECT s FROM Store s WHERE" + formula + "<:10 ORDER BY" + formula + "DESC")

    @Query(nativeQuery = true, value =
            "SELECT s.* " +
                    "FROM Store s " +
                    "WHERE (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(s.latitude)))) < 10 " +
                    "ORDER BY (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(s.latitude)))) DESC")
    List<Store> findStore(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude
    );
}
