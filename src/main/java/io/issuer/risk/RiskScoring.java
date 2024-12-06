package io.issuer.risk;

public interface RiskScoring {
    int riskScore(String cardNumber, String merchant, int amount);
}
