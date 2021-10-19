package com.java.demo_ttcscn.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = {
    CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS
  };
  @Autowired private UserDetailsService userDetailsService;
  @Autowired private DataSource dataSource;
  @Autowired private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  @Autowired private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Bean
  public static ServletListenerRegistrationBean httpSessionEventPublisher() {
    return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/login", "/logout", "/api/**").permitAll();
    http.authorizeRequests().antMatchers("/filter").access("hasRole('ROLE_USER')");
    http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");
    http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

    http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
    http.authorizeRequests()
        .and()
        .formLogin()
        .loginProcessingUrl("/security_check")
        .loginPage("/login")
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .logoutUrl("/logout")
        .clearAuthentication(true)
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true);
    http.authorizeRequests()
        .and()
        .rememberMe()
        .tokenRepository(this.persistentTokenRepository()) //
        .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

    http.sessionManagement()
        .sessionFixation()
        .newSession()
        //        .invalidSessionUrl("/login?message=timeout")
        .maximumSessions(1) // 3
        .maxSessionsPreventsLogin(true) // 4
        //        .expiredUrl("/login?message=max_session")
        .sessionRegistry(sessionRegistry());
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    SessionRegistry sessionRegistry = new SessionRegistryImpl();
    return sessionRegistry;
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }
}
