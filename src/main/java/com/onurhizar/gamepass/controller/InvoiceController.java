package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.PaymentRequest;
import com.onurhizar.gamepass.model.response.InvoiceResponse;
import com.onurhizar.gamepass.service.InvoiceService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceResponse> listInvoices(){
        return invoiceService.listInvoices()
                .stream().map(InvoiceResponse::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public InvoiceResponse getInvoice(@PathVariable String id){
        return InvoiceResponse.fromEntity(invoiceService.findById(id));
    }

    // POST /invoice/{id}/pay -> Invoice (owner of the invoice only)
    @PostMapping("{id}/pay")
    public InvoiceResponse payInvoice(@PathVariable String id,
                                      @Valid @RequestBody PaymentRequest paymentRequest){
        return InvoiceResponse.fromEntity(invoiceService.payInvoice(id, paymentRequest));
    }
}
