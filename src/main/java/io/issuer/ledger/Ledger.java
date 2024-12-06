package io.issuer.ledger;

import java.time.LocalDateTime;

public interface Ledger {
    public int append(LocalDateTime date, String cardNumber, String merchant, int amount, String decision, String status);
}
