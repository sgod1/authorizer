package io.issuer.authorizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
@ConfigurationProperties("authorizer")
public class AuthorizerApplication implements ApplicationListener<ApplicationStartedEvent> {

	Logger logger = LoggerFactory.getLogger(AuthorizerApplication.class);

	private String issuerProcessorHost;
	private String rulesProcessorHost;
	private String riskProcessorHost;

	private int issuerProcessorPort;
	private int rulesProcessorPort;
	private int riskProcessorPort;

	private String cardApplicationTopic;
	private String riskScoringTopic;

	private long riskFactor1;
	private long riskFactor2;

	@Value("${spring.profiles.active:}")
	private String activeProfiles;

	public String getIssuerProcessorHost() {return issuerProcessorHost;}
	public void setIssuerProcessorHost(String issuerProcessorHost) {this.issuerProcessorHost = issuerProcessorHost;}

	public String getRulesProcessorHost() {return rulesProcessorHost;}
	public void setRulesProcessorHost(String rulesProcessorHost) {this.rulesProcessorHost = rulesProcessorHost;}

	public String getRiskProcessorHost() {return riskProcessorHost;}
	public void setRiskProcessorHost(String riskProcessorHost) {this.riskProcessorHost = riskProcessorHost;}

	public int getIssuerProcessorPort() {return issuerProcessorPort;}
	public void setIssuerProcessorPort(int issuerProcessorPort) {this.issuerProcessorPort = issuerProcessorPort;}

	public int getRulesProcessorPort() {return rulesProcessorPort;}
	public void setRulesProcessorPort(int rulesProcessorPort) {this.rulesProcessorPort = rulesProcessorPort;}

	public int getRiskProcessorPort() {return riskProcessorPort;}
	public void setRiskProcessorPort(int riskProcessorPort) {this.riskProcessorPort = riskProcessorPort;}

	public String getCardApplicationTopic() {return cardApplicationTopic;}
	public void setCardApplicationTopic(String cardApplicationTopic) {this.cardApplicationTopic = cardApplicationTopic;}

	public String getRiskScoringTopic() {return riskScoringTopic;}
	public void setRiskScoringTopic(String riskScoringTopic) {this.riskScoringTopic = riskScoringTopic;}

	public long getRiskFactor1() {return riskFactor1;}
	public long getRiskFactor2() {return riskFactor2;}

	public void setRiskFactor1(int riskFactor1) {this.riskFactor1 = riskFactor1;}
	public void setRiskFactor2(int riskFactor2) {this.riskFactor2 = riskFactor2;}

	public static void main(String[] args) {
		SpringApplication.run(AuthorizerApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		logger.info("Authorizer started, active profiles: {}", activeProfiles);
	}

	@Override
	public boolean supportsAsyncExecution() {
		return ApplicationListener.super.supportsAsyncExecution();
	}
}
