package com.java.demo_ttcscn.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    DefaultSavedRequest defaultSavedRequest =
        (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    if (roles.contains("ROLE_ADMIN")) {
      response.sendRedirect("/admin");
      return;
    }
    if (defaultSavedRequest != null) {
      getRedirectStrategy().sendRedirect(request, response, defaultSavedRequest.getRedirectUrl());
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }
}
