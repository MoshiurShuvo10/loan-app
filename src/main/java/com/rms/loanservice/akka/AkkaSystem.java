package com.rms.loanservice.akka;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AkkaSystem {
    @Bean
    public ActorSystem<Void> typedActorSystem() {
        return ActorSystem.create(Behaviors.empty(), "loan-verification-system");
    }
}
