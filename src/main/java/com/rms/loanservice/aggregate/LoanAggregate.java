package com.rms.loanservice.aggregate;

import com.rms.loanservice.command.StartLoanVerificationCommand;
import com.rms.loanservice.command.SubmitLoanApplicationCommand;
import com.rms.loanservice.event.LoanApplicationSubmittedEvent;
import com.rms.loanservice.event.LoanVerificationStartedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class LoanAggregate {
    @AggregateIdentifier
    private String applicationId ;
    private String customerName ;
    private double amount ;
    private String status ;

    @CommandHandler
    public LoanAggregate(SubmitLoanApplicationCommand command) {
        log.info("Handling command: {}", command);
        log.info("id: {}", command.getApplicationId());
        apply(new LoanApplicationSubmittedEvent(command.getApplicationId(), command.getCustomerName(),command.getAmount())) ;
    }

    @EventSourcingHandler
    public void on(LoanApplicationSubmittedEvent event) {
        log.info("Handling event: " + event);
        this.applicationId = event.getApplicationId();
        this.customerName = event.getCustomerName();
        this.amount = event.getAmount();
        this.status = "Submitted";
    }

    @CommandHandler
    public void handle(StartLoanVerificationCommand command) {
        log.info("Starting loan verification for id: {}", command.getApplicationId());
        apply(new LoanVerificationStartedEvent(command.getApplicationId())) ;
    }

    @EventSourcingHandler
    public void on(LoanVerificationStartedEvent event) {
        log.info("Started loan verification for id: {}", event.getApplicationId());
        this.status = "Verification_Started";
    }



















}
