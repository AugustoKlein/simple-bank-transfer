CREATE TABLE account
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    balance BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    account_sender_id   BIGINT NOT NULL,
    account_receiver_id BIGINT NOT NULL,
    amount_transferred  BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_account_sender FOREIGN KEY (account_sender_id) REFERENCES account (id),
    CONSTRAINT fk_account_receiver FOREIGN KEY (account_receiver_id) REFERENCES account (id)
);

CREATE TABLE notification
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    message    VARCHAR(255) NOT NULL,
    account_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account (id)
);