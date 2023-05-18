package com.onurhizar.gamepass.config;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.service.*;
import javax.transaction.Transactional;
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
        ContractRecord contractRecord = createContractRecord("5b8a3d25-2b7a-4683-89ed-ac0e42cdc879"); // contract record of admin
        ContractRecord contractRecord2 = createContractRecord("aa59d163-5e7e-4290-b6ac-b901b0b4543a"); // contract record of test@mail
        // contractRecordService.createInvoice(contractRecord); // create invoice for admin
    }


    private ContractRecord createContractRecord(String userId){
        // simulate that admin user subscribes 6 months pack
        return userService.subscribe(userId,
                "66b455da-665a-4dc1-b4f1-b526c1c9ab4e");
    }

}
