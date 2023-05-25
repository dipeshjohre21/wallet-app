package com.nextuple.walletapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Document(collection = "Transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    @Field(name = "transaction_type", targetType = FieldType.STRING)
    private TransactionType transactionType;
    private String username;
    private String debitedFrom;
    private String creditedTo;
    private int amount;
}
