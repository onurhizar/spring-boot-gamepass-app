package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Payment;
import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.repository.InvoiceRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository repository;
    private final PaymentService paymentService;

    public List<Invoice> listInvoices(){
        return repository.findAll();
    }

    public Invoice addInvoice(Invoice invoice){
        return repository.save(invoice);
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

    /**
     * to list invoices that are older than 15 days from now so that we can downgrade the user to guest role
     */
    public List<Invoice> findInvoicesBy15DaysOld(){
        ZonedDateTime the15DaysOldTime = ZonedDateTime.now().minusDays(15);
        return repository.findByCreatedAtBefore(the15DaysOldTime);
    }

    // TODO remove this method
    public List<Invoice> findInvoicesBy5MinsOld(){
        ZonedDateTime the5MinsOldTime = ZonedDateTime.now().minusMinutes(5);
        return repository.findByCreatedAtBefore(the5MinsOldTime);
    }
}
