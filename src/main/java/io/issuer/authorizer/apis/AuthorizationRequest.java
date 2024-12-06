package io.issuer.authorizer.apis;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class AuthorizationRequest {
    @NotEmpty
    private String cardNumber;
    @NotEmpty
    private String merchant;
    @Min(0)
    private int chargeAmount;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String cardNumber, String merchant, int chargeAmount) {
        this.cardNumber = cardNumber;
        this.merchant = merchant;
        this.chargeAmount = chargeAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
}
