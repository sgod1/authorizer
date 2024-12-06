package io.issuer.authorizer.clients;

import io.issuer.authorizer.apis.RulesRequest;
import io.issuer.authorizer.apis.RulesResponse;
import io.issuer.decisions.AuthorizerRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Qualifier("client")
public class RulesClient implements AuthorizerRules {

    Logger logger = LoggerFactory.getLogger(RulesClient.class);

    @Value("${authorizer.rules-processor-host}")
    public String rulesProcessorHost;

    @Value("${authorizer.rules-processor-port}")
    public int rulesProcessorPort;

    public RulesClient() {
    }

    @Override
    public String applyRules(String cardNumber, String merchant, int chargeAmount) {

        final String uri = "http" + "://" + this.rulesProcessorHost + ":" + String.valueOf(this.rulesProcessorPort) + "/api/rules/decision";

        logger.info("calling ... {}", uri);

        RestClient restClient = RestClient.create();

        RulesRequest body = new RulesRequest();

        body.setMerchant(merchant);
        body.setCardNumber(cardNumber);
        body.setChargeAmount(chargeAmount);

        RulesResponse response = restClient.post()
                .uri(uri).contentType(MediaType.APPLICATION_JSON)
                .body(body).retrieve().toEntity(RulesResponse.class).getBody();

        assert response != null;
        return response.getDecision();
    }
}
