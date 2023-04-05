package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.repository.ContractRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ContractRecordService {
    private final ContractRecordRepository repository;

    /** Copies subscription details to avoid fee change (denormalization) */
    public ContractRecord addContract(User user, Subscription subscription){
        return repository.save(ContractRecord.builder()
                .name(subscription.getName())
                .monthlyFee(subscription.getMonthlyFee())
                .duration(subscription.getDuration())
                .isActive(subscription.isActive())
                .user(user)
                .createdDate(ZonedDateTime.now())
                .build());
    }

    public ContractRecord updateContract(ContractRecord contractRecord, Subscription subscription){
        contractRecord.setName(subscription.getName());
        contractRecord.setDuration(subscription.getDuration());
        contractRecord.setMonthlyFee(subscription.getMonthlyFee());
        return repository.save(contractRecord);
    }
}
