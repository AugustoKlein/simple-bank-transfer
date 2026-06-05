package com.simple_bank_transfer.transaction.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_sender_id")
    private Long accountSenderId;

    @Column(name = "account_receiver_id")
    private Long accountReceiverId;

    @Column(name = "amount_transferred")
    private Long amountTransferred;
}