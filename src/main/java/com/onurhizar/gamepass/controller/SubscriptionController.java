package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
