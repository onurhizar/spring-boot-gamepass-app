package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ContractRecordService contractRecordService;

    @Scheduled(cron = "0 * * * * *")
    public void createInvoicesInEveryMinute(){
        log.info("1 minute has passed");

        // for each contract record, create invoice
        List<ContractRecord> contractRecords = contractRecordService.findAll();
        for (ContractRecord contractRecord : contractRecords) {
            if (contractRecord.isActive()) log.info("Contract record: "+contractRecord.toString());
            contractRecordService.createInvoice(contractRecord);
        }

        // if not paid invoice, make the user guest status TODO
    }

}
