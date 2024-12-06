package io.issuer.authorizer.apis;

public class CardApplicationResponse {
    private String cardNumber;

    public CardApplicationResponse() {
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
