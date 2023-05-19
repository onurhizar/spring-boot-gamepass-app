package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
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

    // FOR TESTING PURPOSES you can use "0 */2 * * * *" to run every 2 minutes (even minutes)
    @Scheduled(cron = "0 0 0 L * *")
    public void createInvoicesInLastDayOfMonth(){
        List<ContractRecord> activeContractRecords = contractRecordService.findActiveContractRecords();
        log.info("Create invoices for active contract records");
        log.info("Active contract records: "+activeContractRecords.size());

        for (ContractRecord contractRecord : activeContractRecords) {
            contractRecordService.createInvoice(contractRecord);
        }

    }

    // FOR TESTING PURPOSES you can use "0 1/2 * * * *" to run every 2 minutes (odd minutes)
    @Scheduled(cron = "0 0 0 15 * *")
    public void checkUnpaidPastInvoicesIn15thDayOfMonth(){
        log.info("Check for non paid invoices and downgrade corresponding users role to guest");
        HashSet<String> userIds = findPastInvoicesAndExtractTheirUserIds();
        downgradeUsersToGuestStatus(userIds);
    }

    private HashSet<String> findPastInvoicesAndExtractTheirUserIds(){
        HashSet<String> userIds = new HashSet<>();

        List<Invoice> oldInvoices = invoiceService.findInvoicesBy15DaysOld(); // 15 days passed invoices // todo use 15 old
        log.info("Old Non Paid invoices: "+oldInvoices.size());
        for (Invoice invoice : oldInvoices) {
            log.info("Invoice: "+invoice);
            log.info("UserID: "+invoice.getContractRecord().getUser().getId());
            log.info("User Email: "+invoice.getContractRecord().getUser().getEmail());
            userIds.add(invoice.getContractRecord().getUser().getId());
        }

        return userIds;
    }

    private void downgradeUsersToGuestStatus(HashSet<String> userIds){
        for (String userId : userIds) userService.downgradeUserRoleToGuest(userId);
    }

}
