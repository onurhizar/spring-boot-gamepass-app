package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
}
