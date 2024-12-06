package io.issuer.authorizer.services;

import io.issuer.decisions.AuthorizerRules;
import io.issuer.risk.RiskScoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class RulesService implements AuthorizerRules {

    private final RiskScoring riskScoring;

    @Autowired
    public RulesService(@Qualifier("client") RiskScoring riskScoring) {
        this.riskScoring = riskScoring;
    }

    @Override
    public String applyRules(String cardNumber, String merchant, int chargeAmount) {

        // risk score
        int riskScore = this.riskScoring.riskScore(cardNumber, merchant, chargeAmount);

        // other business rules...
        return "approved";
    }
}
