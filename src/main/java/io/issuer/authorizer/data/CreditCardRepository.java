package io.issuer.authorizer.data;

import io.issuer.authorizer.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByCardNumber(String cardNumber);
}