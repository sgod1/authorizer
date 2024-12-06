package io.issuer.authorizer.apis;

import jakarta.validation.constraints.NotEmpty;

public class CardApplicationRequest {
    @NotEmpty private String cardType; // visa, mc, ...
    @NotEmpty private String cardBranding; // issuer, gm, ford, ...
    @NotEmpty private String holderName;
    @NotEmpty private String holderEmail;
    @NotEmpty private String cardNumber;

    public CardApplicationRequest() {
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardBranding() {
        return cardBranding;
    }

    public void setCardBranding(String cardBranding) {
        this.cardBranding = cardBranding;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderEmail() {
        return holderEmail;
    }

    public void setHolderEmail(String holderEmail) {
        this.holderEmail = holderEmail;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
