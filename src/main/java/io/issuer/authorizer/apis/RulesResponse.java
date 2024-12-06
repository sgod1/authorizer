package io.issuer.authorizer.apis;

public class RulesResponse {
    private String decision;

    public RulesResponse() {
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
