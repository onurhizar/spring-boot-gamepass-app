package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.model.request.CreateSubscriptionRequest;
import com.onurhizar.gamepass.model.request.UpdateSubscriptionRequest;
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

    public Subscription deleteById(String id) {
        Subscription subscription = findById(id);
        subscription.setActive(false); // soft delete
        return repository.save(subscription);
    }

    public Subscription addSubscription(CreateSubscriptionRequest request) {
        Subscription subscription = Subscription.builder()
                .name(request.getName())
                .monthlyFee(request.getMonthlyFee())
                .duration(request.getDuration())
                .isActive(request.isActive())
                .build();
        return repository.save(subscription);
    }

    public Subscription updateSubscription(String id, UpdateSubscriptionRequest request) {
        Subscription subscription = findById(id);
        subscription.setName(request.getName());
        subscription.setMonthlyFee(request.getMonthlyFee());
        subscription.setDuration(request.getDuration());
        subscription.setActive(request.isActive());
        return repository.save(subscription);
    }

}
