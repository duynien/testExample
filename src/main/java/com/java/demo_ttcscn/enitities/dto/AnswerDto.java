package com.java.demo_ttcscn.enitities.dto;

import java.io.Serializable;

public class AnswerDto implements Serializable {
  private int id;
  private String answ_A;
  private String answ_B;
  private String answ_C;
  private String answ_D;
  private String correct_ans;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAnsw_A() {
    return answ_A;
  }

  public void setAnsw_A(String answ_A) {
    this.answ_A = answ_A;
  }

  public String getAnsw_B() {
    return answ_B;
  }

  public void setAnsw_B(String answ_B) {
    this.answ_B = answ_B;
  }

  public String getAnsw_C() {
    return answ_C;
  }

  public void setAnsw_C(String answ_C) {
    this.answ_C = answ_C;
  }

  public String getAnsw_D() {
    return answ_D;
  }

  public void setAnsw_D(String answ_D) {
    this.answ_D = answ_D;
  }

  public String getCorrect_ans() {
    return correct_ans;
  }

  public void setCorrect_ans(String correct_ans) {
    this.correct_ans = correct_ans;
  }
}
