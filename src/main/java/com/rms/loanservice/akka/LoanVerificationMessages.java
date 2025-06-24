package com.rms.loanservice.akka;

public class LoanVerificationMessages {
    //initial verification message
    public static class StartVerification implements CoordinatorCommand{
        public final String applicationId ;
        public StartVerification(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    // KYC done message
    public static class KycVerified implements CoordinatorCommand {
        public final String applicationId ;
        public KycVerified(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    // doc done message
    public static class DocVerified implements CoordinatorCommand {
        public final String applicationId ;
        public DocVerified(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    public interface CoordinatorCommand {}
}
