package com.rms.loanservice.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.*;
import com.rms.loanservice.gateway.LoanEventPublisher;

public class LoanVerificationCoordinatorActor extends AbstractBehavior<LoanVerificationMessages.CoordinatorCommand> {

    private final LoanEventPublisher publisher ;
    private String applicationId;
    private boolean kycDone = false;
    private boolean docDone = false;

    private ActorRef<String> kycActor ;
    private ActorRef<String> docActor ;

    public LoanVerificationCoordinatorActor(ActorContext<LoanVerificationMessages.CoordinatorCommand> ctx, LoanEventPublisher publisher) {
        super(ctx);
        this.publisher = publisher;
    }

    public static Behavior<LoanVerificationMessages.CoordinatorCommand> create(LoanEventPublisher publisher) {
        return Behaviors.setup(ctx -> new LoanVerificationCoordinatorActor(ctx,publisher)) ;
    }

    @Override
    public Receive<LoanVerificationMessages.CoordinatorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoanVerificationMessages.StartVerification.class, this::onStart)
                .onMessage(LoanVerificationMessages.KycVerified.class, this::onKycDone)
                .onMessage(LoanVerificationMessages.DocVerified.class, this::onDocDone)
                .build();
    }

    private Behavior<LoanVerificationMessages.CoordinatorCommand> onStart(LoanVerificationMessages.StartVerification command) {
        this.applicationId = command.applicationId ;
        kycActor = getContext().spawn(KycActor.create(getContext().getSelf(), publisher), "KycActor-" + applicationId) ;
        docActor = getContext().spawn(DocActor.create(getContext().getSelf()), "DocActor-" + applicationId) ;

        kycActor.tell(applicationId);
        docActor.tell(applicationId);
        return this ;
    }
    private Behavior<LoanVerificationMessages.CoordinatorCommand> onKycDone(LoanVerificationMessages.KycVerified msg) {
        kycDone = true;
        checkIfComplete();
        return this;
    }

    private Behavior<LoanVerificationMessages.CoordinatorCommand> onDocDone(LoanVerificationMessages.DocVerified msg) {
        docDone = true;
        checkIfComplete();
        return this;
    }

    private void checkIfComplete() {
        if (kycDone && docDone) {
            getContext().getLog().info("All verifications done for {}", applicationId);
            publisher.publishLoanVerificationEvent(applicationId);
        }
    }

}
