package com.simple_bank_transfer.notification.repository.entity;

import com.simple_bank_transfer.notification.enums.StatusEnum;
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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
