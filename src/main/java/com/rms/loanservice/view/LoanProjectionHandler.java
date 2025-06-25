package com.rms.loanservice.view;

import com.rms.loanservice.event.*;
import com.rms.loanservice.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoanProjectionHandler {
    private final LoanRepository loanRepository;

    @EventHandler
    public void on(LoanApplicationSubmittedEvent loanApplicationSubmittedEvent) {
        LoanProjection loanProjection = new LoanProjection();
        loanProjection.setApplicationId(loanApplicationSubmittedEvent.getApplicationId());
        loanProjection.setCustomerName(loanApplicationSubmittedEvent.getCustomerName());
        loanProjection.setAmount(loanApplicationSubmittedEvent.getAmount());
        loanProjection.setStatus("Submitted");
        loanRepository.save(loanProjection);
    }

    @EventHandler
    public void on(LoanApprovedEvent loanApprovedEvent) {
        LoanProjection loanProjection = loanRepository
                .findLoanProjectionByApplicationId(loanApprovedEvent.getApplicationId())
                .orElseThrow(()-> new IllegalStateException("No loan found for applicationId: " + loanApprovedEvent.getApplicationId()));

        loanProjection.setStatus("Approved");
        loanRepository.save(loanProjection);
    }

    @EventHandler
    public void on(LoanRejectedEvent loanRejectedEvent) {
        LoanProjection loanProjection = loanRepository
                .findLoanProjectionByApplicationId(loanRejectedEvent.getApplicationId())
                .orElseThrow(()-> new IllegalStateException("No loan found for applicationId: " + loanRejectedEvent.getApplicationId()));

        loanProjection.setStatus("Rejected");
        loanRepository.save(loanProjection);
    }

    @EventHandler
    public void on(LoanVerificationStartedEvent loanVerificationStartedEvent) {
        LoanProjection loanProjection = loanRepository
                .findLoanProjectionByApplicationId(loanVerificationStartedEvent.getApplicationId())
                .orElseThrow(()-> new IllegalStateException("No loan found for applicationId: " + loanVerificationStartedEvent.getApplicationId()));

        loanProjection.setStatus("Verification_Started");
        loanRepository.save(loanProjection);
    }

    @EventHandler
    public void on(LoanVerifiedEvent loanVerifiedEvent) {
        LoanProjection loanProjection = loanRepository
                .findLoanProjectionByApplicationId(loanVerifiedEvent.getApplicationId())
                .orElseThrow(()-> new IllegalStateException("No loan found for applicationId: " + loanVerifiedEvent.getApplicationId()));

        loanProjection.setStatus("Verified");
        loanRepository.save(loanProjection);
    }
}
