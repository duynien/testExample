package com.java.demo_ttcscn.services;

import com.java.demo_ttcscn.enitities.enumValue.ERole;
import com.java.demo_ttcscn.enitities.model.Role;
import com.java.demo_ttcscn.enitities.model.User;
import com.java.demo_ttcscn.enitities.payload.LoginRequest;
import com.java.demo_ttcscn.enitities.payload.SignupRequest;
import com.java.demo_ttcscn.enitities.result.Response;
import com.java.demo_ttcscn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class LoginService {
  @Autowired AuthenticationManager authenticationManager;
  @Autowired private PasswordEncoder encoder;
  @Autowired private UserRepository userRepository;

  public Response handleLogin(LoginRequest loginRequest) {
    Response res;
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      res = new Response(true, userDetails);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
      res = new Response(false, "Lỗi");
      return res;
    }
  }

  @Transactional
  public Response handleSignup(SignupRequest signupRequest) {
    User user = new User();
    Set<Role> roles = new HashSet<>();
    Response res;
    try {
      Role role = new Role(1, ERole.ROLE_USER);
      roles.add(role);
      user.setRoles(roles);
      user.setEmail(signupRequest.getEmail());
      user.setUsername(signupRequest.getUsername());
      user.setPassword(encoder.encode(signupRequest.getPassword()));
      userRepository.save(user);
      res = new Response(true, user);
      return res;
    } catch (Exception e) {
      res = new Response(false, "Lỗi");
      return res;
    }
  }

  public boolean isUniqueUser(String email, String username) {
    User user = userRepository.findUniqueByEmailAndUsername(email, username);
    if (user != null) {
      return false;
    } else {
      return true;
    }
  }
  public Response isUniqueUserByEmail(String email){
    User user = userRepository.findUniqueByEmail(email);
    if (user != null) {
      return new Response(false,"Email đã tồn tại");
    } else {
      return new Response(true,"Hợp lệ");
    }
  }
  public Response isUniqueUserByUsername(String username){
    User user = userRepository.findUniqueByUsername(username);
    if (user != null) {
      return new Response(false,"Tài khoản đã tồn tại");
    } else {
      return new Response(true,"Hợp lệ");
    }
  }
}
