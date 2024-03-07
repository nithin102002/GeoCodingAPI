package com.example.FindingNearestStore.Model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ResponseLog")
public class ResponseLog {
    @Id
    private String id;
    private String subscriptionId;
    private Date date;
    private String requestMethod;
    private String requestURI;
    private String requestBody;
    private String responseBody;
    private String status;
//    private List<ResponseLogDTO> response;
}
