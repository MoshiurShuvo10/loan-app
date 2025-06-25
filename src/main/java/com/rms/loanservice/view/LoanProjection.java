package com.rms.loanservice.view;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_view")
@Data
public class LoanProjection {
    @Id
    private String applicationId ;
    private String customerName ;
    private double amount ;
    private String status ;
}
