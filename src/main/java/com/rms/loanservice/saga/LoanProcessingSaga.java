package com.rms.loanservice.saga;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Props;
import com.rms.loanservice.akka.LoanVerificationCoordinatorActor;
import com.rms.loanservice.akka.LoanVerificationMessages;
import com.rms.loanservice.command.StartLoanVerificationCommand;
import com.rms.loanservice.event.LoanApplicationSubmittedEvent;
import com.rms.loanservice.event.LoanVerificationStartedEvent;
import com.rms.loanservice.gateway.LoanEventPublisher;
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

    @Autowired
    private transient akka.actor.typed.ActorSystem<Void> typedActorSystem;
    @Autowired
    private LoanEventPublisher loanEventPublisher;

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
        log.info("Triggering typed Akka verification for app: {} ", loanVerificationStartedEvent.getApplicationId());
        ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator =
                typedActorSystem.systemActorOf(
                        LoanVerificationCoordinatorActor.create(loanEventPublisher),
                        "Coordinator-"+loanVerificationStartedEvent.getApplicationId(),
                        Props.empty()
                ) ;
        coordinator.tell(new LoanVerificationMessages.StartVerification(loanVerificationStartedEvent.getApplicationId()));
    }
}
