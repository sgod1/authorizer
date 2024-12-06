package io.issuer.authorizer.services;

//import com.instana.sdk.annotation.Span;
//import com.instana.sdk.support.SpanSupport;
import io.issuer.risk.RiskScoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class RiskService implements RiskScoring {

    @Value("${authorizer.risk-factor-1}")
    public long riskFactor1;

    @Value("${authorizer.risk-factor-2}")
    public long riskFactor2;

    @Value("${authorizer.risk-scoring-topic}")
    private String riskScoringTopic;

    private final EventPublishingService eventPublishingService;

    @Autowired
    public RiskService(EventPublishingService eventPublishingService) {
        this.eventPublishingService = eventPublishingService;
    }

    @Override
    //@Span(value = "risk-score", capturedStackFrames = 20, type = Span.Type.INTERMEDIATE)
    public int riskScore(String cardNumber, String merchant, int amount) {

        //SpanSupport.annotate(Span.Type.INTERMEDIATE, "risk-score", "tags.instana.java.sdk", "Success");
        long riskFactor = computeRiskFactor(riskFactor1, riskFactor2);

        String message = cardNumber + "-" + merchant + "-" + amount + "-" + String.valueOf(riskFactor);
        eventPublishingService.publish(riskScoringTopic, message);

        return riskFactor > 1000 ? 1 : -1;
    }

    public long computeRiskFactor(long prime1, long prime2) {
        long p12 = prime1 * prime2;

        long k = 1;
        while (true) {
            if (p12 / k == prime2) {
                return k;
            }
            k++;
        }
    }
}
