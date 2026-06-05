package com.simple_bank_transfer.notification.repository;

import com.simple_bank_transfer.notification.repository.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
