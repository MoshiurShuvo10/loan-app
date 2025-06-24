package com.rms.loanservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoanRejectedEvent {
    private final String applicationId ;
    private final String reason ;


}
