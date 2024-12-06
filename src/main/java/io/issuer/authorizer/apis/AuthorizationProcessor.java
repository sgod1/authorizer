package io.issuer.authorizer.apis;

import io.issuer.authorizer.services.AuthorizationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/authorization")
@Profile({"authorizer", "local"})
public class AuthorizationProcessor {
    Logger logger = LoggerFactory.getLogger(AuthorizationProcessor.class);

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationProcessor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/charge", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody AuthorizationResponse authorize(@Valid @RequestBody AuthorizationRequest authorizationRequest) {
        logger.info("Authorize request: {}", authorizationRequest);

        LocalDateTime now = LocalDateTime.now(); // transaction received

        String cardNumber = authorizationRequest.getCardNumber();
        String merchant = authorizationRequest.getMerchant();
        int chargeAmount = authorizationRequest.getChargeAmount();

        String decision = this.authorizationService.authorize(cardNumber, merchant, chargeAmount);

        return new AuthorizationResponse(decision);
    }
}
