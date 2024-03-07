package com.example.FindingNearestStore.Repository;

import com.example.FindingNearestStore.Model.ResponseLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseLogRepository extends MongoRepository<ResponseLog,String> {
}
