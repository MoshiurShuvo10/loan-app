package com.rms.loanservice.saga;

import com.rms.loanservice.command.StartLoanVerificationCommand;
import com.rms.loanservice.event.LoanApplicationSubmittedEvent;
import com.rms.loanservice.event.LoanVerificationStartedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;

@Saga
@Slf4j
public class LoanProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    public LoanProcessingSaga() {}

    @StartSaga
    @SagaEventHandler(associationProperty = "applicationId")
    public void on(LoanApplicationSubmittedEvent loanApplicationSubmittedEvent) {
        log.info("Saga started for applicationId: " + loanApplicationSubmittedEvent.getApplicationId());
        associateWith("applicationId", loanApplicationSubmittedEvent.getApplicationId());
        commandGateway.send(new StartLoanVerificationCommand(loanApplicationSubmittedEvent.getApplicationId())) ;
    }

    @SagaEventHandler(associationProperty = "applicationId")
    public void on(LoanVerificationStartedEvent loanVerificationStartedEvent) {
        log.info("loan verification started for applicationId: " + loanVerificationStartedEvent.getApplicationId());
    }
}
