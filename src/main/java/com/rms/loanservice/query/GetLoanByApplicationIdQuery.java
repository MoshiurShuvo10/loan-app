package com.rms.loanservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetLoanByApplicationIdQuery {
    private String applicationId ;
}
