//package com.nextuple.walletapi.controller;
//
//import com.nextuple.walletapi.WalletapiApplication;
//import com.nextuple.walletapi.model.TransactionType;
//import com.nextuple.walletapi.model.Transactions;
//import com.nextuple.walletapi.payload.request.AmountTransferRequest;
//import com.nextuple.walletapi.payload.request.RechargeRequest;
//import com.nextuple.walletapi.repository.UserRepository;
//import com.nextuple.walletapi.service.WalletService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest({WalletapiApplication.class})
//public class WalletControllerTest {
//    @Mock
//    private WalletService walletService;
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private WalletController walletController;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
////    @Test
////    public void walletRechargeTest() {
////        ResponseEntity<Object> entity = new ResponseEntity<>("Recharged Successfully", HttpStatus.OK);
////        RechargeRequest rechargeRequest = new RechargeRequest(1500);
////        String token = "token";
////        when(walletService.walletRecharge(anyString(), any(RechargeRequest.class))).thenReturn(entity);
////
////        ResponseEntity<Object> responseEntity = walletController.recharge(token, rechargeRequest);
////        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////        verify(walletService, times(1)).walletRecharge(anyString(), any(RechargeRequest.class));
////    }
//
//    @Test
//    public void amountTransferTest_Success() {
//        ResponseEntity<Object> entity = new ResponseEntity<>("Transaction Successfull", HttpStatus.OK);
//        AmountTransferRequest amountTransferRequest = new AmountTransferRequest("deepak", 2000);
//        String token = "token";
//
//        when(walletService.amountTransfer(anyString(), any(AmountTransferRequest.class))).thenReturn(entity);
//        ResponseEntity<Object> responseEntity = walletController.amountTransfer(token, amountTransferRequest);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void amountTransferTest_WhenRecieverIsNotAvailable() {
//        ResponseEntity<Object> entity = new ResponseEntity<>("No User is available with this username", HttpStatus.NOT_FOUND);
//        AmountTransferRequest amountTransferRequest = new AmountTransferRequest("deepak", 2000);
//        String token = "token";
//
//        when(walletService.amountTransfer(anyString(), any(AmountTransferRequest.class))).thenReturn(entity);
//        ResponseEntity<Object> responseEntity = walletController.amountTransfer(token, amountTransferRequest);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void amountTransferTest_InsufficientBalance() {
//        ResponseEntity<Object> entity = new ResponseEntity<>("Insufficient Balance", HttpStatus.FORBIDDEN);
//        AmountTransferRequest amountTransferRequest = new AmountTransferRequest("deepak", 2000);
//        String token = "token";
//
//        when(walletService.amountTransfer(anyString(), any(AmountTransferRequest.class))).thenReturn(entity);
//        ResponseEntity<Object> responseEntity = walletController.amountTransfer(token, amountTransferRequest);
//
//        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void showAllTransactionsTest() {
//        List<Transactions> transactionsList = Stream.of(new
//                        Transactions(TransactionType.RECHARGE, "rishabh", "Bank", "wallet", 2000)
//                , new Transactions(TransactionType.DEBIT, "rishabh",
//                        "rishabh", "dipesh", 2000)).collect(Collectors.toList());
//        ResponseEntity<Object> entity = new ResponseEntity<>(transactionsList, HttpStatus.OK);
//
//        when(walletService.showAllTransactions(anyString())).thenReturn(entity);
//
//        ResponseEntity<Object> responseEntity = walletController.showTransactions(anyString());
//        List<Transactions> transactionsList1 = (List<Transactions>) responseEntity.getBody();
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(2, transactionsList1.size());
//
//    }
//
//    @Test
//    public void showBalanceTest() {
//        ResponseEntity<Object> entity = new ResponseEntity<>(2000, HttpStatus.OK);
//
//        when(walletService.showBalance(anyString())).thenReturn(entity);
//
//        ResponseEntity<Object> responseEntity = walletController.showBalance(anyString());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(2000,
//                responseEntity.getBody());
//    }
//
//}
