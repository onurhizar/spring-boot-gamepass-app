package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.CreateSubscriptionRequest;
import com.onurhizar.gamepass.model.request.UpdateSubscriptionRequest;
import com.onurhizar.gamepass.model.response.SubscriptionResponse;
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
    public List<SubscriptionResponse> listSubscriptions(){
        return subscriptionService.listSubscriptions();
    }

    @GetMapping("/{id}")
    public SubscriptionResponse getSubscriptionById(@PathVariable String id){
        return SubscriptionResponse.fromEntity(subscriptionService.findById(id));
    }

    @PostMapping
    public SubscriptionResponse postSubscription(@RequestBody CreateSubscriptionRequest request){
        return SubscriptionResponse.fromEntity(subscriptionService.addSubscription(request));
    }

    @PutMapping("/{id}")
    public SubscriptionResponse updateSubscriptionById(@PathVariable String id, @RequestBody UpdateSubscriptionRequest request){
        return SubscriptionResponse.fromEntity(subscriptionService.updateSubscription(id, request));
    }

    @DeleteMapping("/{id}")
    public SubscriptionResponse deleteSubscriptionById(@PathVariable String id){
        return SubscriptionResponse.fromEntity(subscriptionService.deleteById(id));
    }

}
