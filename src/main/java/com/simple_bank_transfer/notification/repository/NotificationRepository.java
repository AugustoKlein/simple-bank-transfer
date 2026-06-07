package com.simple_bank_transfer.notification.repository;

import com.simple_bank_transfer.notification.enums.StatusEnum;
import com.simple_bank_transfer.notification.repository.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByStatus(StatusEnum status);
}
