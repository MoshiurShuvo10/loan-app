package com.rms.loanservice.query;

/*private final OrderViewRepository repository;

public OrderQueryHandler(OrderViewRepository repository) {
    this.repository = repository;
}

@QueryHandler
public List<OrderView> handle(GetOrdersByUserIdQuery query) {
    return repository.findByUserId(query.userId());
}*/

import com.rms.loanservice.repository.LoanRepository;
import com.rms.loanservice.view.LoanProjection;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LoanQueryHandler {
    private final LoanRepository loanRepository;

    @QueryHandler
    public Optional<LoanProjection> handle(GetLoanByApplicationIdQuery getLoanByApplicationIdQuery) {
        return loanRepository.findLoanProjectionByApplicationId(getLoanByApplicationIdQuery.getApplicationId()) ;
    }
}
