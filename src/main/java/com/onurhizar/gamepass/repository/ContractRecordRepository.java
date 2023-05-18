package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
    public List<ContractRecord> findByActiveIsTrue();
}
