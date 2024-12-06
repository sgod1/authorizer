CREATE SEQUENCE IF NOT EXISTS authorizations_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS credit_cards_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE authorizations
(
    id               BIGINT  NOT NULL,
    transaction_date TIMESTAMP WITHOUT TIME ZONE,
    card_number      VARCHAR(255),
    merchant         VARCHAR(255),
    charge_amount    INTEGER NOT NULL,
    decision         VARCHAR(255),
    status           VARCHAR(255),
    CONSTRAINT pk_authorizations PRIMARY KEY (id)
);

CREATE TABLE credit_cards
(
    id                BIGINT  NOT NULL,
    issuer            VARCHAR(255),
    type              VARCHAR(255),
    branding          VARCHAR(255),
    card_holder_name  VARCHAR(255),
    card_holder_email VARCHAR(255),
    card_number       VARCHAR(255),
    issue_date        TIMESTAMP WITHOUT TIME ZONE,
    expiration_date   TIMESTAMP WITHOUT TIME ZONE,
    credit_limit      INTEGER NOT NULL,
    status            VARCHAR(255),
    CONSTRAINT pk_credit_cards PRIMARY KEY (id)
);

ALTER TABLE credit_cards
    ADD CONSTRAINT uc_credit_cards_cardnumber UNIQUE (card_number);