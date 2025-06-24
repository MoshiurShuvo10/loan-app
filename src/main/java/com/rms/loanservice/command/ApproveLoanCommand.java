package com.rms.loanservice.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class ApproveLoanCommand {
    @TargetAggregateIdentifier
    private final String applicationId ;

}
