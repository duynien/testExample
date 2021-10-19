package com.java.demo_ttcscn;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.DecimalFormat;

@SpringBootApplication
@EnableJpaAuditing
public class DemoTtcscnApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoTtcscnApplication.class, args);
  }

  //  @Bean
  //  public PasswordEncoder encoder() {
  //    return new BCryptPasswordEncoder();
  //  }
  @Bean
  public DecimalFormat df() {
    return new DecimalFormat("0.00");
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
