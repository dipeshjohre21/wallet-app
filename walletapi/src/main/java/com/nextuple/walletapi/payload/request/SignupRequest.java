package com.nextuple.walletapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Username can not be blank")
    private String username;
    @NotBlank(message = "Password field can not be blank")
    private String password;
    @Email(message = "Invalid email Id")
    private String email;
}