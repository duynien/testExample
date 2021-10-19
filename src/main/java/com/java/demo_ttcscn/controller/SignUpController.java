package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class SignUpController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/email")
    public ResponseEntity<?> checkSinupByEmail(@RequestParam String email){
        return ResponseEntity.ok(loginService.isUniqueUserByEmail(email));
    }
    @GetMapping("/username")
    public ResponseEntity<?> checkSinupByUsername(@RequestParam String username){
        return ResponseEntity.ok(loginService.isUniqueUserByUsername(username));
    }
}
