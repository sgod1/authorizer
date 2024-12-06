package io.issuer.authorizer.clients;

import io.issuer.authorizer.apis.RiskRequest;
import io.issuer.authorizer.apis.RiskResponse;
import io.issuer.risk.RiskScoring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@Qualifier("client")
public class RiskClient implements RiskScoring {

    Logger logger = LoggerFactory.getLogger(RiskClient.class);

    @Value("${authorizer.risk-processor-host}")
    public String riskProcessorHost;

    @Value("${authorizer.risk-processor-port}")
    public int riskProcessorPort;

    public RiskClient() {
    }

    @Override
    public int riskScore(String cardNumber, String merchant, int amount) {

        final String uri = "http" + "://" + this.riskProcessorHost + ":" + String.valueOf(this.riskProcessorPort) + "/api/risk/score";

        logger.info("calling ... {}", uri);

        RestClient restClient = RestClient.create();

        RiskRequest body = new RiskRequest();

        body.setMerchant(merchant);
        body.setCardNumber(cardNumber);
        body.setAmount(amount);

        RiskResponse response = restClient.post()
                .uri(uri).contentType(MediaType.APPLICATION_JSON)
                .body(body).retrieve().toEntity(RiskResponse.class).getBody();

        assert response != null;
        return response.getScore();
    }
}
