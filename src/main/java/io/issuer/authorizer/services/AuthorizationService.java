package io.issuer.authorizer.services;

import io.issuer.cards.Issuer;
import io.issuer.decisions.AuthorizerRules;
import io.issuer.ledger.Ledger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthorizationService {

    private final Issuer issuerService;
    private final AuthorizerRules rulesService;
    private final Ledger ledgerService;

    @Autowired
    public AuthorizationService(@Qualifier("client") Issuer issuerService, @Qualifier("client") AuthorizerRules rulesService, Ledger ledgerService) {
        this.issuerService = issuerService;
        this.rulesService = rulesService;
        this.ledgerService = ledgerService;
    }

    public String authorize(String cardNumber, String merchant, int chargeAmount) {

        LocalDateTime now = LocalDateTime.now();

        String cardStatus = this.issuerService.queryCardStatus(cardNumber);
        String decision = this.rulesService.applyRules(cardNumber, merchant, chargeAmount);
        int append = this.ledgerService.append(now, cardNumber, merchant, chargeAmount, decision, "ok");

        return decision;
    }
}
