package io.issuer.cards;

public interface Issuer {
    public String queryCardStatus(String cardNumber);
    public int queryCreditLimit(String cardNumber);
    public int queryCurrentBalance(String cardNumber);
    public String applyForCard(String cardType, String cardBrand, String holderName, String holderEmail, String cardNumber);
}
