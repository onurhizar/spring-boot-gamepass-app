package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Payment;
import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository repository;
    private final PaymentService paymentService;

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


    @Transactional
    public Invoice payInvoice(String invoiceId, PaymentRequest paymentRequest) {
        Invoice invoice = findById(invoiceId);
        int amount = paymentRequest.getAmount();

        // validations, TODO : prettify later, custom annotation based validations
        if(amount<1) throw new IllegalStateException("amount cannot be lesser than 1");
        else if (amount > invoice.getFee()) {
            throw new IllegalStateException("amount cannot be greater than invoice fee");
        }

        invoice.setFee(invoice.getFee()-amount);
        Payment payment = Payment.builder()
                .amount(paymentRequest.getAmount())
                .invoice(invoice)
                .senderCard(paymentRequest.getSenderCard())
                .receiverCard(paymentRequest.getReceiverCard())
                .build();

        paymentService.addPayment(payment);
        return invoice;
    }

    public Invoice findById(String id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
