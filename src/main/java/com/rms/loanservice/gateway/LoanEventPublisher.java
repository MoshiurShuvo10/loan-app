package com.rms.loanservice.gateway;


import com.rms.loanservice.event.LoanVerifiedEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.springframework.stereotype.Component;

@Component
public class LoanEventPublisher {
    private final EventBus eventBus;
    public LoanEventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    public void publishLoanVerificationEvent(String applicationId) {
        eventBus.publish(GenericEventMessage.asEventMessage(new LoanVerifiedEvent(applicationId)));
    }
}
