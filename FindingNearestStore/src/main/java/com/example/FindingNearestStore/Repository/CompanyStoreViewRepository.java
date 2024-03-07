package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.CompanyStoreViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CompanyStoreViewRepository extends JpaRepository<CompanyStoreViews,String> {




    @Query(nativeQuery = true, value =
            "SELECT s.* " +
                    "FROM company_store_view s  " +
                    "WHERE s.company_id= :companyId AND (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(s.latitude)))) < :distance " +
                    "ORDER BY (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(s.latitude)))) DESC")
    List<CompanyStoreViews> findStore(BigDecimal latitude, BigDecimal longitude, Integer distance, String companyId);
    @Query(nativeQuery = true, value = "select s.* from company_store_view s where company_name= ;")
    List<CompanyStoreViews> findStores(String  companyName);
}
//    create view company_store_view as
//    select s.*, c.company_name from store s
//        left join company c
//        on c.comapny_id = s.company_id;


//    create view plan_subscription_company_view as select p.*,s.subscription_id,s.expiry_date,s.company_id,s.created_date,c.company_name from plan p
//        left join subscription s
//        on p.plan_id = s.plan_id
//        left join company c
//        on s.company_id = c.comapny_id;