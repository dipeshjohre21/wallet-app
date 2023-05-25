package com.nextuple.walletapi.service.impl;

import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.model.TransactionType;
import com.nextuple.walletapi.model.Transactions;
import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.AmountTransferRequest;
import com.nextuple.walletapi.payload.request.RechargeRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.repository.TransactionRepository;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.WalletService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class
WalletServiceImpl implements WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JWTUtil jwtUtil;


    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public ResponseEntity<MessageResponse> walletRecharge(String token, RechargeRequest rechargeRequest) {
        try {
            User savedUser = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
            if (savedUser == null) {
                String message = "User not found";
                logger.error(message);
                return new ResponseEntity<>(new MessageResponse(message), HttpStatus.NOT_FOUND);
            }

            Transactions transaction = new Transactions(TransactionType.RECHARGE, savedUser.getUsername(), "Bank", "Wallet", rechargeRequest.getAmount());
            int amount = savedUser.getWalletAmount() + rechargeRequest.getAmount();
            savedUser.setWalletAmount(amount);
            userRepository.save(savedUser);
            transactionRepository.save(transaction);
            logger.info("Recharged Successfully");
            return new ResponseEntity<>(new MessageResponse("Recharged Successfully"), HttpStatus.OK);
        } catch (DataAccessException e) {
            String message = "Database error occurred";
            logger.error(message, e);
            return new ResponseEntity<>(new MessageResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JwtException e) {
            String message = "Invalid JWT token";
            logger.error(message, e);
            return new ResponseEntity<>(new MessageResponse(message), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            String message = "An error occurred during the wallet recharge";
            logger.error(message, e);
            return new ResponseEntity<>(new MessageResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public ResponseEntity<MessageResponse> amountTransfer(String token, AmountTransferRequest amountTransferRequest) {
        try {
            User receiver = userRepository.findByUsername(amountTransferRequest.getUsername());
            if (receiver == null) {
                logger.error("No user is available with username-{}", amountTransferRequest.getUsername());
                return new ResponseEntity<>(new MessageResponse("No user is available with username-" + amountTransferRequest.getUsername()), HttpStatus.NOT_FOUND);
            }

            User sender = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
            if (sender.getWalletAmount() < amountTransferRequest.getAmount()) {
                logger.error("Insufficient balance. Sender has {} but needs {}", sender.getWalletAmount(), amountTransferRequest.getAmount());
                return new ResponseEntity<>(new MessageResponse("Insufficient Balance"), HttpStatus.FORBIDDEN);
            }

            sender.setWalletAmount(sender.getWalletAmount() - amountTransferRequest.getAmount());
            receiver.setWalletAmount(receiver.getWalletAmount() + amountTransferRequest.getAmount());

            Transactions senderTransaction = new Transactions(TransactionType.DEBIT, sender.getUsername(), sender.getUsername(), receiver.getUsername(), amountTransferRequest.getAmount());
            Transactions receiverTransaction = new Transactions(TransactionType.CREDIT, receiver.getUsername(), sender.getUsername(), receiver.getUsername(), amountTransferRequest.getAmount());

            userRepository.save(sender);
            userRepository.save(receiver);
            transactionRepository.save(senderTransaction);
            transactionRepository.save(receiverTransaction);

            return new ResponseEntity<>(new MessageResponse("Transaction Successful"), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("An error occurred: {}", e.getMessage());
            return new ResponseEntity<>(new MessageResponse("An error occurred while processing the transaction"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            return new ResponseEntity<>(new MessageResponse("An unexpected error occurred while processing the transaction"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> showAllTransactions(String token) {
        try {
            User savedUser = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
            if (savedUser == null) {
                String message = "User not found";
                logger.error(message);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            logger.info("Transactions retrieved successfully");
            return new ResponseEntity<>((transactionRepository.findAllByUsername(jwtUtil.getUsernameFromToken(token))), HttpStatus.OK);
        } catch (JwtException e) {
            String message = "Invalid JWT token";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (DataAccessException e) {
            String message = "Database error occurred";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            String message = "An error occurred while retrieving transactions";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> showBalance(String token) {
        try {
            User savedUser = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
            if (savedUser == null) {
                String message = "User not found";
                logger.error(message);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
            logger.info("Wallet balance retrieved successfully");
            return new ResponseEntity<>(savedUser.getWalletAmount(), HttpStatus.OK);
        } catch (JwtException e) {
            String message = "Invalid JWT token";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (DataAccessException e) {
            String message = "Database error occurred";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            String message = "An error occurred while retrieving wallet balance";
            logger.error(message, e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
