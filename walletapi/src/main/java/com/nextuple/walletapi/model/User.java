package com.nextuple.walletapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String username;
    private String password;
    private String email;
    private int walletAmount;

    public User(String username, String password, String email, int walletAmount) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.walletAmount = walletAmount;
    }
}