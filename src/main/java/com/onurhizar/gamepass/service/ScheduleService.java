package com.onurhizar.gamepass.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Slf4j
public class ScheduleService {

    @Scheduled(cron = "0 * * * * *")
    public void logEveryMinute(){
        // log.info("1 minute has passed");
    }
}
