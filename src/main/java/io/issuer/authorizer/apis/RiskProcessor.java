package io.issuer.authorizer.apis;

import io.issuer.risk.RiskScoring;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@Profile({"risk", "local"})
public class RiskProcessor {
    Logger logger = LoggerFactory.getLogger(RiskProcessor.class);

    private final RiskScoring riskScoring;

    @Autowired
    public RiskProcessor(@Qualifier("default") RiskScoring riskScoring) {
        this.riskScoring = riskScoring;
    }

    @PostMapping(value = "/score", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RiskResponse scoreRisk(@Valid @RequestBody RiskRequest riskRequest) {
        logger.info(riskRequest.toString());

        String cardNumber = riskRequest.getCardNumber();
        String merchant = riskRequest.getMerchant();
        int amount = riskRequest.getAmount();

        int score = this.riskScoring.riskScore(cardNumber, merchant, amount);

        RiskResponse riskResponse = new RiskResponse();
        riskResponse.setScore(score);

        return riskResponse;
    }
}
