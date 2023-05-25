package com.nextuple.walletapi.service.impl;

import com.nextuple.walletapi.jwt.JWTUtil;
import com.nextuple.walletapi.model.User;
import com.nextuple.walletapi.payload.request.LoginRequest;
import com.nextuple.walletapi.payload.request.SignupRequest;
import com.nextuple.walletapi.payload.response.LoginResponse;
import com.nextuple.walletapi.payload.response.MessageResponse;
import com.nextuple.walletapi.payload.response.SignupResponse;
import com.nextuple.walletapi.repository.UserRepository;
import com.nextuple.walletapi.security.service.UserDetailsImpl;
import com.nextuple.walletapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity<Object> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtil.generateTokenFromUsername(loginRequest.getUsername());
            logger.info("User {} successfully logged in.", userDetails.getUsername());
            return ResponseEntity.ok().body(new LoginResponse(userDetails.getUsername(), userDetails.getEmail(), token));
        } catch (AuthenticationException e) {
            logger.warn("Invalid username or password for user {}.", loginRequest.getUsername());
            throw new BadCredentialsException("Invalid username or password", e);
        } catch (Exception e) {
            logger.error("An error occurred while trying to login for user {}.", loginRequest.getUsername(), e);
            throw new RuntimeException("An error occurred while trying to login", e);
        }
    }

    public ResponseEntity<Object> signup(SignupRequest signupRequest) {
        try {
            if (userRepository.existsByUsername(signupRequest.getUsername())) {
                logger.warn("User already exists for this username: " + signupRequest.getUsername());
                return new ResponseEntity<>(new MessageResponse("User already exists for this username"), HttpStatus.CONFLICT);
            }

            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                logger.warn("User already exists for this email: " + signupRequest.getEmail());
                return new ResponseEntity<>(new MessageResponse("User already exists for this email"), HttpStatus.CONFLICT);
            }

            User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getEmail(), 0);
            User savedUser = userRepository.save(user);

            logger.info("User registered successfully: " + savedUser.getUsername());
            return new ResponseEntity<>(new SignupResponse(savedUser.getUsername(), savedUser.getEmail(), "User registration successful"), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            logger.error("Failed to register user due to database error: " + e.getMessage());
            return new ResponseEntity<>(new MessageResponse("Failed to register user due to database error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Failed to register user due to an unknown error: " + e.getMessage());
            return new ResponseEntity<>(new MessageResponse("Failed to register user due to an unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}