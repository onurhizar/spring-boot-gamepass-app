package com.onurhizar.gamepass.model.response;

import com.onurhizar.gamepass.model.entity.ContractRecord;
import com.onurhizar.gamepass.model.entity.Invoice;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {
    private String id;
    private int fee;
    private ContractRecordResponse contractRecord;

    public static InvoiceResponse fromEntity(Invoice invoice){
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .fee(invoice.getFee())
                .contractRecord(ContractRecordResponse.fromEntity(invoice.getContractRecord()))
                .build();
    }
}
