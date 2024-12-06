package io.issuer.authorizer.apis;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class RiskRequest {
    private @NotEmpty String cardNumber;
    private @NotEmpty String merchant;
    private @Min(0) int amount;

    public RiskRequest() {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
