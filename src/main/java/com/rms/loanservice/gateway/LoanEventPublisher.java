package com.rms.loanservice.gateway;


import com.rms.loanservice.event.LoanRejectedEvent;
import com.rms.loanservice.event.LoanVerifiedEvent;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Component;

@Component
public class LoanEventPublisher {
    private EventGateway eventGateway ;
    public LoanEventPublisher(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }
    public void publishLoanVerificationEvent(String applicationId) {
        eventGateway.publish(GenericEventMessage.asEventMessage(new LoanVerifiedEvent(applicationId)));
    }

    public void publishLoanRejectedEvent(String applicationId, String reason) {
        eventGateway.publish(GenericEventMessage.asEventMessage(new LoanRejectedEvent(applicationId, reason)));
    }
}
