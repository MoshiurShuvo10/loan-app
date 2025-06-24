package com.rms.loanservice.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


public class KycActor extends AbstractBehavior<String> {
    private final ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator;

    private KycActor(ActorContext<String> context,
                     ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator) {
        super(context);
        this.coordinator = coordinator;
    }

    public static Behavior<String> create(ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinatorCommand) {
        return Behaviors.setup(ctx -> new KycActor(ctx,coordinatorCommand));
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessage(String.class, this::verifyKyc)
                .build();
    }

    private Behavior<String> verifyKyc(String applicationId) {
        getContext().getLog().info("Kyc verified for id {} " ,applicationId);
        coordinator.tell(new LoanVerificationMessages.KycVerified(applicationId)) ;
        return this;
    }
}
