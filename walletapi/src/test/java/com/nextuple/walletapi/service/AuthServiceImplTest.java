package com.nextuple.walletapi.service;

import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.payload.response.SignupResponse;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void signupTest_WhenUsernameIsAlreadyTaken() {
        SignupRequest signupRequest = new SignupRequest("rishabh", "123456", "rishabh@gmail.com");
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);
        ResponseEntity<Object> entity = authService.signup(signupRequest);
        Assertions.assertEquals(HttpStatus.CONFLICT, entity.getStatusCode());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void signupTest_Success() {
        SignupRequest request = new SignupRequest("rishabh", "123456", "rishabh@gmail.com");
        User user = new User(request.getUsername(), "encoded_password", request.getEmail(), 0);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("ashd");
        when(userRepository.save(any())).thenReturn(user);
        ResponseEntity<Object> response = authService.signup(request);
        SignupResponse signupResponse = (SignupResponse) response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request.getUsername(), signupResponse.getUsername());
        assertEquals(request.getEmail(), signupResponse.getEmail());
        assertEquals("User registration successful", signupResponse.getMessage());
    }

    @Test
    public void signupTest_WhenEmailIsAlreadyTaken() {
        SignupRequest signupRequest = new SignupRequest("rishabh", "123456", "rishabh@gmail.com");
        when(userRepository.existsByEmail(any())).thenReturn(true);
        ResponseEntity<Object> entity = authService.signup(signupRequest);
        assertEquals(HttpStatus.CONFLICT, entity.getStatusCode());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testSignupException() {
        when(userRepository.save(any())).thenThrow(new RuntimeException("Unknown error"));
        SignupRequest signupRequest = new SignupRequest("username", "password", "email");
        ResponseEntity<Object> response = authService.signup(signupRequest);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Failed to register user due to an unknown error", ((MessageResponse) response.getBody()).getMessage());
    }
}


//    @Test
//    public void loginTest_Success() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser");
//        loginRequest.setPassword("testpassword");
//
//        Authentication authentication = mock(Authentication.class);
//        UserDetailsImpl userDetails = new UserDetailsImpl("testuser", "testemail", "testpassword");
//        String token = "testtoken";
//
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(signatureAlgorithm.HS512).thenReturn(SignatureAlgorithm.valueOf("ABC"));
//        when(jwtUtil.generateTokenFromUsername("testuser")).thenReturn(token);
//
//        ResponseEntity<Object> responseEntity = authService.login(loginRequest);
//
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        LoginResponse loginResponse = (LoginResponse) responseEntity.getBody();
//        Assertions.assertEquals("testuser", loginResponse.getUsername());
//    }

//    public  ResponseEntity<Object> login(LoginRequest loginRequest) {
////        System.out.println(loginRequest);
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        String token = jwtUtil.generateTokenFromUsername(loginRequest.getUsername());
////        System.out.println(loginRequest);
//        return ResponseEntity.ok().body(
//                new LoginResponse(userDetails.getUsername(), userDetails.getEmail(),token)
//        );
//    }


//    @Test
//    void loginTest_Success() {
//        LoginRequest loginRequest = new LoginRequest("rishabh", "password");
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTYyMTYwNjU1NywiZXhwIjoxNjIxNjEyOTU3fQ.dOpvZ5z2Z5q5x_GV7S03L-4I6PNfnuP9lFmk7d4Y9Ck";
//
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetails userDetails = Mockito.mock(UserDetails.class);
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getUsername()).thenReturn(loginRequest.getUsername());
//        when(jwtUtil.generateTokenFromUsername(anyString())).thenReturn(token);
//
//        ResponseEntity<Object> responseEntity = authService.login(loginRequest);
//        LoginResponse response = (LoginResponse) responseEntity.getBody();
//
//        assertNotNull(response);
//        assertEquals(loginRequest.getUsername(), response.getUsername());
//        assertEquals(token, response.getToken());
//    }