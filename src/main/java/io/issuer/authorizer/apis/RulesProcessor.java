package io.issuer.authorizer.apis;

import io.issuer.decisions.AuthorizerRules;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
@Profile({"rules", "local"})
public class RulesProcessor {
    Logger logger = LoggerFactory.getLogger(RulesProcessor.class);

    private final AuthorizerRules rules;

    @Autowired
    public RulesProcessor(@Qualifier("default") AuthorizerRules rules) {
        this.rules = rules;
    }

    @PostMapping(value = "/decision", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RulesResponse applyRules(@Valid @RequestBody RulesRequest rulesRequest) {
        logger.info("Applying rules: {}", rulesRequest);

        String cardNumber = rulesRequest.getCardNumber();
        String merchant = rulesRequest.getMerchant();
        int chargeAmount = rulesRequest.getChargeAmount();

        String decision = this.rules.applyRules(cardNumber, merchant, chargeAmount);

        RulesResponse rulesResponse = new RulesResponse();
        rulesResponse.setDecision(decision);

        return rulesResponse;
    }
}
