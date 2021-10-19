package com.java.demo_ttcscn.enitities.payload;

public class AnswerRequest {
  private String answer;

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "AnswerRequest{" +
            "answer=" + answer +
            '}';
  }
}
