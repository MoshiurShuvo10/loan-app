package com.rms.loanservice.repository;

import com.rms.loanservice.view.LoanProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends MongoRepository<LoanProjection, UUID> {
    Optional<LoanProjection> findLoanProjectionByApplicationId(String applicationId);
}
