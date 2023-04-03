package com.onurhizar.gamepass.repository;

import com.onurhizar.gamepass.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
