package io.issuer.authorizer.services;

import io.issuer.authorizer.data.AuthorizationRepository;
import io.issuer.authorizer.entities.Authorization;
import io.issuer.ledger.Ledger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LedgerService implements Ledger {

    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public LedgerService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public int append(LocalDateTime transactionDate, String cardNumber, String merchant, int chargeAmount, String decision, String status) {

        Authorization authorization = new Authorization();

        authorization.setTransactionDate(transactionDate);
        authorization.setCardNumber(cardNumber);
        authorization.setMerchant(merchant);
        authorization.setChargeAmount(chargeAmount);
        authorization.setDecision(decision);
        authorization.setStatus(status);

        this.authorizationRepository.save(authorization);

        return 0;
    }
}
