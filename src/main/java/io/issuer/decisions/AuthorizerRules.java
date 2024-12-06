package io.issuer.decisions;

public interface AuthorizerRules {
    public String applyRules(String cardNumber, String merchant, int chargeAmount);
}
