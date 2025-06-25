package com.rms.loanservice.controller;

import com.rms.loanservice.command.SubmitLoanApplicationCommand;
import com.rms.loanservice.dto.SubmitLoanDto;
import com.rms.loanservice.query.GetLoanByApplicationIdQuery;
import com.rms.loanservice.view.LoanProjection;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

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

    @GetMapping("/{applicationId}")
    public CompletableFuture<LoanProjection> getLoanStatus(@PathVariable String applicationId) {
        return queryGateway.query(
                new GetLoanByApplicationIdQuery(applicationId),
                LoanProjection.class
        ) ;
    }
}
