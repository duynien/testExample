package com.java.demo_ttcscn.enitities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionForExamDto {

  @JsonProperty("id")
  private int id;

  @JsonProperty("name_question")
  private String name_question;

  @JsonProperty("level")
  private String level;

  @JsonProperty("answ_A")
  private String answerAnsw_A;

  @JsonProperty("answ_B")
  private String answerAnsw_B;

  @JsonProperty("answ_C")
  private String answerAnsw_C;

  @JsonProperty("answ_D")
  private String answerAnsw_D;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName_question() {
    return name_question;
  }

  public void setName_question(String name_question) {
    this.name_question = name_question;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getAnswerAnsw_A() {
    return answerAnsw_A;
  }

  public void setAnswerAnsw_A(String answerAnsw_A) {
    this.answerAnsw_A = answerAnsw_A;
  }

  public String getAnswerAnsw_B() {
    return answerAnsw_B;
  }

  public void setAnswerAnsw_B(String answerAnsw_B) {
    this.answerAnsw_B = answerAnsw_B;
  }

  public String getAnswerAnsw_C() {
    return answerAnsw_C;
  }

  public void setAnswerAnsw_C(String answerAnsw_C) {
    this.answerAnsw_C = answerAnsw_C;
  }

  public String getAnswerAnsw_D() {
    return answerAnsw_D;
  }

  public void setAnswerAnsw_D(String answerAnsw_D) {
    this.answerAnsw_D = answerAnsw_D;
  }
}
