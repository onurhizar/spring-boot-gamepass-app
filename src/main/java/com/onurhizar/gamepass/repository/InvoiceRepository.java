package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByCreatedAtBefore(ZonedDateTime time);
    List<Invoice> findByCreatedAtBeforeAndFeeIsNot(ZonedDateTime time, int fee);

    List<Invoice> findByContractRecordUserIdAndFeeNot(String userId, int fee);
}
