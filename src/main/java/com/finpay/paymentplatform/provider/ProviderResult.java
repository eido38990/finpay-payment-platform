package com.finpay.paymentplatform.provider;

public class ProviderResult {

    private final boolean success;
    private final String providerReference;
    private final String failureReason;

    public ProviderResult(
            boolean success,
            String providerReference,
            String failureReason) {

        this.success = success;
        this.providerReference = providerReference;
        this.failureReason = failureReason;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
