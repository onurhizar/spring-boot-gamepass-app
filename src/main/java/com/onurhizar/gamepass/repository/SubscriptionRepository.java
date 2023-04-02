package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
}
