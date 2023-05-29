package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByCreatedAtBefore(ZonedDateTime time);
    List<Invoice> findByCreatedAtBeforeAndFeeIsNot(ZonedDateTime time, int fee);

    /** Finds invoices for a user */
    List<Invoice> findByContractRecordUserId(String userId);

    /** Finds invoices for a user that are not paid and past-due */
    List<Invoice> findByContractRecordUserIdAndFeeNotAndCreatedAtBefore(String userId, int fee, ZonedDateTime time);

    /** returns invoices for a single contract record only after given created time <br>
     * it is used to list invoices for a contract record in current month */
    List<Invoice> findByContractRecordIdAndCreatedAtAfter(String contractRecordId, ZonedDateTime time);
}
