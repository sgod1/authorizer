package io.issuer.authorizer.services;

import io.issuer.authorizer.data.CreditCardRepository;
import io.issuer.authorizer.entities.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Qualifier("default")
public class IssuerService implements io.issuer.cards.Issuer {
    Logger logger = LoggerFactory.getLogger(IssuerService.class);

    private final CreditCardRepository cardRepository;
    private final EventPublishingService eventPublishingService;

    @Value("${authorizer.card-application-topic}")
    public String cardApplicationTopic;

    @Autowired
    public IssuerService(final CreditCardRepository cardRepository, final EventPublishingService eventPublishingService) {
        this.cardRepository = cardRepository;
        this.eventPublishingService = eventPublishingService;
    }

    @Override
    public String queryCardStatus(String cardNumber) {

        final String message = String.format("Query card status service injecting warning for card number %s", cardNumber);
        logger.warn(message);

        List<CreditCard> cards = this.cardRepository.findByCardNumber(cardNumber);

        if (cards.size() == 1) {
            return cards.get(0).getStatus();

        } else if (cards.isEmpty()) {
            throw new IllegalStateException("No cards found for given card number " + cardNumber);
        }
        else {
            throw new IllegalStateException("Multiple cards found for given card number " + cardNumber);
        }
    }

    @Override
    public int queryCreditLimit(String cardNumber) {
        List<CreditCard> cards = this.cardRepository.findByCardNumber(cardNumber);
        CreditCard cc = cards.get(0);
        return cc.getCreditLimit();
    }

    @Override
    public int queryCurrentBalance(String cardNumber) {
        return 100;
    }

    @Override
    public String applyForCard(String cardType, String cardBranding, String holderName, String holderEmail, String cardNumber) {

        // issue card
        CreditCard cc = issueCard(cardType, cardBranding, holderName, holderEmail, cardNumber);

        // publish card application event
        String message = String.format("%s-%s-%s-%s-%s", cardType, cardBranding, holderName, holderEmail, cardNumber);
        eventPublishingService.publish(cardApplicationTopic, message);

        return cc.getCardNumber();
    }

    private CreditCard issueCard(String cardType, String cardBranding, String holderName, String holderEmail, String cardNumber) {
        CreditCard cc = new CreditCard();

        cc.setCardHolderName(holderName);
        cc.setCardHolderEmail(holderEmail);
        cc.setType(cardType);
        cc.setBranding(cardBranding);

        cc.setCardNumber(cardNumber);
        cc.setIssueDate(LocalDateTime.now());
        cc.setExpirationDate(LocalDateTime.now().plusYears(1));

        cc.setCreditLimit(1000);
        cc.setStatus("active");

        cc =  this.cardRepository.save(cc);
        return cc;
    }
}
