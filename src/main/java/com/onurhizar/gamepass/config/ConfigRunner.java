package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConfigRunner implements CommandLineRunner {

    private final GameService gameService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ContractRecordService contractRecordService;
    private final InvoiceService invoiceService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ContractRecord contractRecord = createContractRecord(); // contract record of admin
        createInvoice(contractRecord);
        createInvoice(contractRecord);
    }


    private ContractRecord createContractRecord(){
        // simulate that admin user subscribes 6 months pack
        return userService.subscribe("5b8a3d25-2b7a-4683-89ed-ac0e42cdc879",
                "66b455da-665a-4dc1-b4f1-b526c1c9ab4e");
    }

    private void createInvoice(ContractRecord contractRecord){
        invoiceService.createInvoice(contractRecord);
        int remainingInvoices = contractRecord.getDuration();
        log.info("Invoice created, remaining: "+remainingInvoices);
    }
}
