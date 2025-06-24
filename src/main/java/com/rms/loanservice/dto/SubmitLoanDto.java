package com.rms.loanservice.dto;

import lombok.Data;

@Data
public class SubmitLoanDto {
    private String applicationId ;
    private String customerName ;
    private double amount ;
}
