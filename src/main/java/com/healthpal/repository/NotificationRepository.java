package com.healthpal.repository;

import com.healthpal.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//rahaf's part
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}
