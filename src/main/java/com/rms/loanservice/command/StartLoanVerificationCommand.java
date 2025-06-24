package com.rms.loanservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class StartLoanVerificationCommand {
    @TargetAggregateIdentifier
    private final String applicationId;
}
