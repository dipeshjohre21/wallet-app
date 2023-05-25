//package com.nextuple.walletapi.advice;
//
//
//import io.jsonwebtoken.JwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
//        String message = "Database error occurred";
//        logger.error(message, ex);
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<Object> handleJwtException(JwtException ex) {
//        String message = "Invalid JWT token";
//        logger.error(message, ex);
//        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
//    }

//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleException(Exception ex) {
//        String message = "An error occurred during the wallet recharge";
//        logger.error(message, ex);
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}
//
