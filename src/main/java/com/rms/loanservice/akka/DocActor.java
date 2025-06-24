package com.rms.loanservice.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.rms.loanservice.saga.LoanProcessingSaga;

public class DocActor extends AbstractBehavior<String> {
    private final ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator ;
    private DocActor(ActorContext<String> context
    , ActorRef<LoanVerificationMessages.CoordinatorCommand> coordinator) {
        super(context);
        this.coordinator = coordinator;
    }

    public static Behavior<String> create(ActorRef<LoanVerificationMessages.CoordinatorCommand> coorrdinatorCommand) {
        return Behaviors.setup(ctx -> new DocActor(ctx, coorrdinatorCommand));
    }
    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessage(String.class, this::docVerified)
                .build();
    }

    private Behavior<String> docVerified(String applicationId) {
        getContext().getLog().info("Doc verified for id {}", applicationId);
        coordinator.tell(new LoanVerificationMessages.DocVerified(applicationId));
        return this ;
    }
}
