package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.exception.UnacceptableRequestException;
import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.model.entity.Payment;
import com.onurhizar.gamepass.model.entity.User;
import com.onurhizar.gamepass.model.enums.UserRole;
import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.repository.InvoiceRepository;
import javax.transaction.Transactional;

import com.onurhizar.gamepass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
    private final InvoiceRepository repository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

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
        if(amount<1) throw new UnacceptableRequestException("amount cannot be lesser than 1");
        else if (amount > invoice.getFee()) {
            throw new UnacceptableRequestException("amount cannot be greater than invoice fee");
        }

        int remainingFee = invoice.getFee()-amount;

        invoice.setFee(remainingFee);
        Payment payment = Payment.builder()
                .amount(paymentRequest.getAmount())
                .invoice(invoice)
                .senderCard(paymentRequest.getSenderCard())
                .receiverCard(paymentRequest.getReceiverCard())
                .build();

        paymentService.addPayment(payment);

        if (remainingFee!=0) return invoice;

        /* if remaining fee is 0, then invoice is paid.
        Check if user is GUEST and upgrade to MEMBER if all invoices are paid */
        ContractRecord contractRecord = invoice.getContractRecord();
        User user = contractRecord.getUser();

        if (!user.getRole().equals(UserRole.GUEST)) return invoice;

        List<Invoice> unpaidInvoices = findAllNonPaidPastDueInvoicesForSpecificUser(user.getId());
        log.info("unpaid invoices:"+unpaidInvoices.size());

        // TODO note that, if user role is ADMIN, new role will be MEMBER. Ignore this possibility
        if (unpaidInvoices.isEmpty()) {
            upgradeUserRoleToMember(user.getId());
            log.info("User is UPGRADED to MEMBER, usedId: " + user.getId());
        }

        return invoice;
    }

    public Invoice findById(String id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /** to list invoices that are older than 15 days from now so that we can downgrade the user to guest role */
    public List<Invoice> findInvoicesBy15DaysOld(){
        ZonedDateTime the15DaysOldTime = ZonedDateTime.now().minusDays(15);
        return repository.findByCreatedAtBefore(the15DaysOldTime);
    }

    public List<Invoice> findAllNonPaidPastDueInvoicesForSpecificUser(String userId){
        ZonedDateTime the5MinsOldTime = ZonedDateTime.now().minusMinutes(5).plusSeconds(2);
        return repository.findByContractRecordUserIdAndFeeNotAndCreatedAtBefore(userId, 0, the5MinsOldTime);
    }

    /** lists invoices for a user that are created in the last 1 month */
    public List<Invoice> findInvoicesOfContractRecordInCurrentMonth(String contractRecordId){
        // get current month's first day from hour 00:00:00.000
        ZonedDateTime currentMonthStartingTime = ZonedDateTime.now().withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        return repository.findByContractRecordIdAndCreatedAtAfter(contractRecordId, currentMonthStartingTime);
    }

    // TODO remove this method later, resolve circular dependency
    private void upgradeUserRoleToMember(String userId){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setRole(UserRole.MEMBER);
        userRepository.save(user);
    }
}
