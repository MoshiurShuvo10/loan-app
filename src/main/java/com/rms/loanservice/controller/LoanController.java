package com.rms.loanservice.controller;

import com.rms.loanservice.command.SubmitLoanApplicationCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {
    private final CommandGateway commandGateway;

    @GetMapping
    public String test() {
        return "test";
    }
    @PostMapping
    public String submitLoan(@RequestParam String customerName, @RequestParam double amount) {
        String applicationId = UUID.randomUUID().toString();
        System.out.println("Application Id: " + applicationId);
        var submitLoanCommand = new SubmitLoanApplicationCommand(applicationId, customerName, amount);
        commandGateway.send(submitLoanCommand);
        return "Loan application submitted with id " + applicationId;
    }
}
