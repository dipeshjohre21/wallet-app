package com.nextuple.walletapi.controller;

import com.nextuple.walletapi.payload.request.AmountTransferRequest;
import com.nextuple.walletapi.payload.request.RechargeRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/recharge")
    public ResponseEntity<MessageResponse> recharge(@RequestHeader("Authorization") String token, @Valid @RequestBody RechargeRequest rechargeRequest) {

        return walletService.walletRecharge(token, rechargeRequest);
    }

    @PostMapping("/pay")
    public ResponseEntity<MessageResponse> amountTransfer(@RequestHeader("Authorization") String token, @Valid @RequestBody AmountTransferRequest amountTransferRequest) {
        System.out.println("-----");
        return walletService.amountTransfer(token, amountTransferRequest);
    }

    @GetMapping("/show-balance")
    public ResponseEntity<Object> showBalance(@RequestHeader("Authorization") String token) {
        return walletService.showBalance(token);
    }

    @GetMapping("/show-all-transactions")
    public ResponseEntity<Object> showTransactions(@RequestHeader("Authorization") String token) {
        return walletService.showAllTransactions(token);
    }


}
