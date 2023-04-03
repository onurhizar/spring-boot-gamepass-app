package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.model.response.InvoiceResponse;
import com.onurhizar.gamepass.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("{id}/pay")
    public InvoiceResponse payInvoice(@PathVariable String id,
                                      @Valid @RequestBody PaymentRequest paymentRequest){
        return InvoiceResponse.fromEntity(invoiceService.payInvoice(id, paymentRequest));
    }
}
