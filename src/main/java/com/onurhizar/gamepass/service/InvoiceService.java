package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository repository;

    public List<Invoice> listInvoices(){
        return repository.findAll();
    }

    @Transactional
    public void createInvoice(ContractRecord contractRecord){
        int duration = contractRecord.getDuration(); // remaining invoices
        if (duration<1) return; // TODO : throw exception?
        contractRecord.setDuration(duration-1); // decrease the duration
        // TODO : no need to contractRepository save?

        repository.save(Invoice.builder()
            .fee(contractRecord.getMonthlyFee())
            .contractRecord(contractRecord)
            .build());
    }

    public Invoice getInvoice(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
