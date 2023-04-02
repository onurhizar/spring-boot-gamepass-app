package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.response.InvoiceResponse;
import com.onurhizar.gamepass.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceResponse> listInvoices(){
        return invoiceService.listInvoices()
                .stream().map(InvoiceResponse::fromEntity).toList();
    }

    @GetMapping("{id}")
    public InvoiceResponse getInvoice(@PathVariable String id){
        return InvoiceResponse.fromEntity(invoiceService.getInvoice(id));
    }

    // POST /invoice/{id}/pay -> Invoice (owner of the invoice only)
}
