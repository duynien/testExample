package com.java.demo_ttcscn.enitities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultQuestionDto {
  @JsonProperty("username")
  private String username;

  @JsonProperty("code")
  private String codeQues;

  @JsonProperty("name")
  private String name;

  @JsonProperty("number_question")
  private int numberQuestion;

  @JsonProperty("number_point")
  private float numberPoint;

  public String getCodeQues() {
    return codeQues;
  }

  public void setCodeQues(String codeQues) {
    this.codeQues = codeQues;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumberQuestion() {
    return numberQuestion;
  }

  public void setNumberQuestion(int numberQuestion) {
    this.numberQuestion = numberQuestion;
  }

  public float getNumberPoint() {
    return numberPoint;
  }

  public void setNumberPoint(float numberPoint) {
    this.numberPoint = numberPoint;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
