package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.payload.SignupRequest;
import com.java.demo_ttcscn.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Controller
public class LoginSignupController {
  @Autowired private LoginService loginService;

  @GetMapping(value = "/login")
  public String login(
      @RequestParam(required = false) String message,
      Model model,
      @CookieValue(defaultValue = "", value = "username") String username) {
    if (message != null && !message.isEmpty()) {
      if (message.equals("max_session")) {
        model.addAttribute("message", "Tài khoản đã được đăng nhập tại thiết bị khác");
      }
//      if (message.equals("logout")) {
//        model.addAttribute("message", "Logout!");
//      }
      if (message.equals("error")) {
        model.addAttribute("message", "Đăng nhập sai tài khoản mật khẩu");
      }
      if (message.equals("exist")) {
        model.addAttribute("message", "Tài khoản đã tồn tại");
      }
    }
    model.addAttribute("username", username);
    return "login";
  }

  @GetMapping("/sign-up")
  String getSignUp() {
    return "sign-up";
  }

  @PostMapping("/sign-up")
  @Transactional
  String signUp(
      Model model, @ModelAttribute SignupRequest signupRequest, HttpServletResponse response) {
    try {
      String username = signupRequest.getUsername();
      Cookie cookie = new Cookie("username", username);
      cookie.setMaxAge(7 * 24 * 60 * 60);
      response.addCookie(cookie);
      loginService.handleSignup(signupRequest);
      return "redirect:/login";
    } catch (Exception e) {
      return "redirect:/login?message=exist";
    }
  }
}
