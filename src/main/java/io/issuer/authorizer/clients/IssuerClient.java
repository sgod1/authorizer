package io.issuer.authorizer.clients;

import io.issuer.cards.Issuer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Qualifier("client")
public class IssuerClient implements Issuer {

    Logger logger = LoggerFactory.getLogger(IssuerClient.class);

    @Value("${authorizer.issuer-processor-host}")
    public String issuerProcessorHost;

    @Value("${authorizer.issuer-processor-port}")
    public int issuerProcessorPort;

    @Override
    public String queryCardStatus(String cardNumber) {
        RestClient restClient = RestClient.create();

        // todo: catch exception
        // todo: url configuration parameter
        String uri = "http" + "://" + this.issuerProcessorHost + ":" + String.valueOf(this.issuerProcessorPort)
                + "/api/cards/status?cardNumber=" + cardNumber;

        logger.info("calling ... {}", uri);

        ResponseEntity<String> status = restClient.get().uri(uri).retrieve().toEntity(String.class);

        return status.getBody();
    }

    @Override
    public int queryCreditLimit(String cardNumber) {
        throw new UnsupportedOperationException("card-issuer:query-credit-limit not supported yet.");
    }

    @Override
    public int queryCurrentBalance(String cardNumber) {
        throw new UnsupportedOperationException("card-issuer:query-current-balance not supported yet.");
    }

    @Override
    public String applyForCard(String cardType, String cardBrand, String holderName, String holderEmail, String cardNumber) {
        throw new UnsupportedOperationException("card-issuer:apply-for-card not supported yet.");
    }
}
