package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import com.onurhizar.gamepass.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ContractRecordService contractRecordService;
    private final InvoiceService invoiceService;
    private final UserService userService;
    private final InvoiceRepository invoiceRepository;

    @Scheduled(cron = "0 */2 * * * *")
    public void createInvoicesInEveryEvenMinute(){
        log.info("1 minute has passed");

        // for each contract record, create invoice
        List<ContractRecord> activeContractRecords = contractRecordService.findActiveContractRecords();
        log.info("Active contract records: "+activeContractRecords.size());

        for (ContractRecord contractRecord : activeContractRecords) {
            log.info("Contract record: "+contractRecord);
            contractRecordService.createInvoice(contractRecord);
        }

    }

    @Scheduled(cron = "0 1/2 * * * *")
    public void createInvoicesInEveryOddMinute(){
        // if not paid invoice, make the user guest status TODO
        log.info("Odd minute");
        findPassedInvoicesAndExtractTheirUserIds();

        List<Invoice> invoices = invoiceService.findAllNonPaidPastDueInvoicesForSpecificUser("aa59d163-5e7e-4290-b6ac-b901b0b4543a"); // TODO remove this line
        log.info("Specific user invoices:");
        for (Invoice invoice : invoices) {
            log.info("Invoice: "+invoice);
        }
    }

    private void findPassedInvoicesAndExtractTheirUserIds(){
        HashSet<String> userIds = new HashSet<>();

        //List<Invoice> oldInvoices = invoiceService.findInvoicesBy15DaysOld(); // 15 days passed invoices // todo use 15 old
        List<Invoice> oldInvoices = invoiceService.findNonPaidInvoicesBy5MinsOld(); // 15 days passed invoices // todo use 15 old
        log.info("Old Non Paid invoices: "+oldInvoices.size()); // TODO make it only non zero fee invoices
        for (Invoice invoice : oldInvoices) {
            log.info("Invoice: "+invoice);
            log.info("UserID: "+invoice.getContractRecord().getUser().getId());
            log.info("User Email: "+invoice.getContractRecord().getUser().getEmail());
            userIds.add(invoice.getContractRecord().getUser().getId());
        }

        downgradeUsersToGuestStatus(userIds);
    }

    private void downgradeUsersToGuestStatus(HashSet<String> userIds){
        for (String userId : userIds) userService.downgradeUserRoleToGuest(userId);
    }


    // seconds minutes hours days months day-of-week
    // to repeat in every 2 minutes : 0 */2 * * * *
    // every even minute 0 */2 * * * *
    // every odd minute 0 1/2 * * * *
}
