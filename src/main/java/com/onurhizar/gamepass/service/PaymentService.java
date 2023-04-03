package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Payment;
import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public void addPayment(Payment payment){
        repository.save(payment);
    }
}
