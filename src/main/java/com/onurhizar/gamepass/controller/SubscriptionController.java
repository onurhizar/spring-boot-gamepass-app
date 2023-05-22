package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<Subscription> listSubscriptions(){
        return subscriptionService.listSubscriptions();
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionById(@PathVariable String id){
        return subscriptionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Subscription deleteSubscriptionById(@PathVariable String id){
        return subscriptionService.deleteById(id);
    }

    // TODO add other CRUD operations
}
