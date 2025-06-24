package com.rms.loanservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoanApplicationSubmittedEvent {
        private String applicationId ;
        private String customerName ;
        private double amount ;
}
