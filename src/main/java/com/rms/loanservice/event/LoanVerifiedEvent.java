package com.rms.loanservice.event;

public class LoanVerifiedEvent {
    private final String applicationId;
    public LoanVerifiedEvent(String applicationId) {
        this.applicationId = applicationId;
    }
    public String getApplicationId() {
        return applicationId;
    }
}
