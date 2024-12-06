package io.issuer.authorizer.apis;

import io.issuer.cards.Issuer;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@Profile({"issuer", "local"})
public class CardProcessor {
    Logger logger = LoggerFactory.getLogger(CardProcessor.class);

    private final Issuer issuerService;

    @Autowired
    public CardProcessor(@Qualifier("default") Issuer issuerService) {
        this.issuerService = issuerService;
    }

    @PostMapping(value = "/apply", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CardApplicationResponse applyForCard(@Valid @RequestBody CardApplicationRequest application) {

        String type = application.getCardType();
        String branding = application.getCardBranding();
        String holderName = application.getHolderName();
        String holderEmail = application.getHolderEmail();
        String cardNumber = application.getCardNumber();

        String cardNumberAssigned = this.issuerService.applyForCard(type, branding, holderName, holderEmail, cardNumber);

        CardApplicationResponse response = new CardApplicationResponse();
        response.setCardNumber(cardNumberAssigned);

        return response;
    }

    @GetMapping(value="/status", params = "cardNumber", produces = "application/json")
    public String cardStatus(@PathParam("cardNumber") String cardNumber) {

        final String message = String.format("Top level controller injecting error message for card status query %s", cardNumber);
        logger.error(message);

        return this.issuerService.queryCardStatus(cardNumber);
    }
}
