package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.payload.LoginRequest;
import com.java.demo_ttcscn.enitities.payload.SignupRequest;
import com.java.demo_ttcscn.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginController {
//  @Autowired AuthenticationManager authenticationManager;
  @Autowired LoginService loginService;

  @PostMapping(value = "/login")
  public ResponseEntity<?> authenticateUser(@ModelAttribute LoginRequest loginRequest) {
    return ResponseEntity.ok(loginService.handleLogin(loginRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signupUser(@ModelAttribute SignupRequest signupRequest) {
    return ResponseEntity.ok(loginService.handleSignup(signupRequest));
  }
}
