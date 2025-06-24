package com.rms.loanservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoanVerificationStartedEvent {
    private final String applicationId;
}
