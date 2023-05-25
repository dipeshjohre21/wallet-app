package com.nextuple.walletapi.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountTransferRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private int amount;
}
