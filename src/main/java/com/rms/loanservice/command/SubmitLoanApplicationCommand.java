package com.rms.loanservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SubmitLoanApplicationCommand {
        @TargetAggregateIdentifier
        private String applicationId ;
        private String customerName ;
        private double amount ;
}
