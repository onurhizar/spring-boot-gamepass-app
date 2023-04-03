package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
