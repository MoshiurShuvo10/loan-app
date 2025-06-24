package com.rms.loanservice.controller;

import com.rms.loanservice.command.SubmitLoanApplicationCommand;
import com.rms.loanservice.dto.SubmitLoanDto;
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
    public String submitLoan(@RequestBody SubmitLoanDto submitLoanDto) {
        var submitLoanCommand = new SubmitLoanApplicationCommand(submitLoanDto.getApplicationId(), submitLoanDto.getCustomerName(), submitLoanDto.getAmount());
        commandGateway.send(submitLoanCommand);
        return "Loan application submitted with id " + submitLoanDto.getApplicationId();
    }
}
