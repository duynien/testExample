package com.java.demo_ttcscn.enitities.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {
  @JsonProperty("content")
  public Object content;

  @JsonProperty("number_question")
  private int numberQuestion;

  @JsonProperty("number_success")
  private int numberQuestionCorrect;

  @JsonProperty("point")
  private String point;
}
