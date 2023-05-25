package com.nextuple.walletapi.controller;

import com.nextuple.walletapi.WalletapiApplication;
import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.response.LoginResponse;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.payload.response.SignupResponse;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest({WalletapiApplication.class})
public class AuthControllerTest {
    @Mock
    private AuthService authService;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginTest_Success() {
        ResponseEntity<Object> entity = new ResponseEntity<>(new LoginResponse("rishabh", "rishabh@gmail.com", "token"), HttpStatus.OK);
        LoginRequest loginRequest = new LoginRequest("rishabh", "123456");

        when(authService.login(any(LoginRequest.class))).thenReturn(entity);

        ResponseEntity<Object> responseEntity = authController.login(loginRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void loginTest_BadCredentials() {
        ResponseEntity<Object> entity = new ResponseEntity<>(new MessageResponse("Bad Credentials"), HttpStatus.BAD_REQUEST);
        LoginRequest loginRequest = new LoginRequest("rishabh", "123456");

        when(authService.login(any(LoginRequest.class))).thenReturn(entity);

        ResponseEntity<Object> responseEntity = authController.login(loginRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void signupTest_Success() {
        ResponseEntity<Object> entity = new ResponseEntity<>(new SignupResponse("rishabh", "rishabh@gmail.com", "User Registration Successfull"), HttpStatus.OK);
        SignupRequest signupRequest = new SignupRequest("rishabh", "123456", "rishabh@gmail.com");

        when(authService.signup(any(SignupRequest.class))).thenReturn(entity);

        ResponseEntity<Object> responseEntity = authController.signup(signupRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void signupTest_BadRequest() {
        ResponseEntity<Object> entity = new ResponseEntity<>(new MessageResponse("Bad Credentials"), HttpStatus.BAD_REQUEST);
        SignupRequest signupRequest = new SignupRequest("rishabh", "123456", "rishabh@gmail.com");

        when(authService.signup(any(SignupRequest.class))).thenReturn(entity);
        ResponseEntity<Object> responseEntity = authController.signup(signupRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
