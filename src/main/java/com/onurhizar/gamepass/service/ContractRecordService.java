package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Subscription;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.repository.ContractRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractRecordService {
    private final ContractRecordRepository repository;
    private final InvoiceService invoiceService;

    /** Copies subscription details to avoid fee change (denormalization) */
    public ContractRecord addContract(User user, Subscription subscription){
        return repository.save(ContractRecord.builder()
                .name(subscription.getName())
                .monthlyFee(subscription.getMonthlyFee())
                .duration(subscription.getDuration())
                .active(subscription.isActive())
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


    @Transactional
    public void createInvoice(ContractRecord contractRecord){
        if (!contractRecord.isActive()) return; // do not create invoice if contract is not active

        int duration = contractRecord.getDuration(); // remaining invoices
        contractRecord.setDuration(duration-1); // decrease the duration
        if (duration==1) contractRecord.setActive(false); // deactivate if duration is 1

        invoiceService.addInvoice(new Invoice(contractRecord.getMonthlyFee(),contractRecord));
        log.info("Invoice created, remaining: " + (duration-1));

        repository.save(contractRecord); // TODO : no need to contractRepository save?
    }

    public List<ContractRecord> findActiveContractRecords() {
        return repository.findByActiveIsTrue();
    }
}
