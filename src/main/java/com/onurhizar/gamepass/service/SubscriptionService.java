package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;

    public List<Subscription> listSubscriptions(){
        return repository.findAll();
    }

    public Subscription findById(String id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
