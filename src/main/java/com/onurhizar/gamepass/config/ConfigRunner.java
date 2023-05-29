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

    private final UserService userService;
    private final ContractRecordService contractRecordService;

    @Override
    public void run(String... args) throws Exception {
        ContractRecord contractRecord = createContractRecord("ad1a1ddd-2f1c-4cc9-85ea-312dfc487bc9",
                "66b455da-665a-4dc1-b4f1-b526c1c9ab4e" ); // contract record of admin
        contractRecordService.createInvoice(contractRecord); // create invoice for admin
    }


    private ContractRecord createContractRecord(String userId, String subscriptionId){
        return userService.subscribe(userId,subscriptionId);
    }

}
