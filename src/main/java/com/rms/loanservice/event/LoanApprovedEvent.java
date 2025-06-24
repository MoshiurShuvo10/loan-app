package com.rms.loanservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanApprovedEvent {
    private final String applicationId;
}
