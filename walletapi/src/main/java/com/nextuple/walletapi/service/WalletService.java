package com.nextuple.walletapi.service;

import com.nextuple.walletapi.payload.request.AmountTransferRequest;
import com.nextuple.walletapi.payload.request.RechargeRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface WalletService {

    ResponseEntity<MessageResponse> walletRecharge(String token, RechargeRequest walletRechargeRequest);

    ResponseEntity<MessageResponse> amountTransfer(String token, AmountTransferRequest walletAmountTransferRequest);

    ResponseEntity<Object> showAllTransactions(String token);

    ResponseEntity<Object> showBalance(String token);


}
