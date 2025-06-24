package com.rms.loanservice.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.rms.loanservice.gateway.LoanEventPublisher;


public class KycActor extends AbstractBehavior<String> {
    private final ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator;
    private final LoanEventPublisher loanEventPublisher;

    private KycActor(ActorContext<String> context,
                     ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator,
                     LoanEventPublisher loanEventPublisher) {
        super(context);
        this.coordinator = coordinator;
        this.loanEventPublisher = loanEventPublisher;
    }

    public static Behavior<String> create(ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinatorCommand, LoanEventPublisher loanEventPublisher) {
        return Behaviors.setup(ctx -> new KycActor(ctx,coordinatorCommand, loanEventPublisher));
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessage(String.class, this::verifyKyc)
                .build();
    }

    private Behavior<String> verifyKyc(String applicationId) {
        getContext().getLog().info("Kyc verification ongoing for id {} " ,applicationId);
        if(applicationId.endsWith("X")) {
            getContext().getLog().info("Kyc verification failed.");
            loanEventPublisher.publishLoanRejectedEvent(applicationId,"app-id ends with X");
        } else {
            getContext().getLog().info("Kyc verified");
            coordinator.tell(new LoanVerificationMessages.KycVerified(applicationId)) ;
        }
        return this;
    }
}
