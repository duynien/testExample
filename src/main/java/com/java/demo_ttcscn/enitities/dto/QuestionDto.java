package com.java.demo_ttcscn.enitities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.demo_ttcscn.enitities.enum_value.Level;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

public class QuestionDto implements Serializable {
  @JsonProperty("id")
  private int id;

  @JsonProperty("name_question")
  private String name_question;

  @JsonProperty("create_at")
  private LocalDateTime create_at;

  @JsonProperty("updated_at")
  private LocalDateTime updated_at;

  @JsonProperty("codequestion_id")
  private int codeQuestionId;
  @JsonProperty("level")
  private String level;

  @JsonProperty("ans_id")
  private int answerId;

  @JsonProperty("answ_A")
  private String answerAnsw_A;

  @JsonProperty("answ_B")
  private String answerAnsw_B;

  @JsonProperty("answ_C")
  private String answerAnsw_C;

  @JsonProperty("answ_D")
  private String answerAnsw_D;

  @JsonProperty("correct_ans")
  private String answerCorrect_ans;

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

  public LocalDateTime getCreate_at() {
    return create_at;
  }

  public void setCreate_at(LocalDateTime create_at) {
    this.create_at = create_at;
  }

  public LocalDateTime getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(LocalDateTime updated_at) {
    this.updated_at = updated_at;
  }

  public int getCodeQuestionId() {
    return codeQuestionId;
  }

  public void setCodeQuestionId(int codeQuestionId) {
    this.codeQuestionId = codeQuestionId;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public int getAnswerId() {
    return answerId;
  }

  public void setAnswerId(int answerId) {
    this.answerId = answerId;
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

  public String getAnswerCorrect_ans() {
    return answerCorrect_ans;
  }

  public void setAnswerCorrect_ans(String answerCorrect_ans) {
    this.answerCorrect_ans = answerCorrect_ans;
  }

  @Override
  public String toString() {
    return "QuestionDto{" +
            "id=" + id +
            ", name_question='" + name_question + '\'' +
            ", create_at=" + create_at +
            ", updated_at=" + updated_at +
            ", codeQuestionId=" + codeQuestionId +
            ", level='" + level + '\'' +
            ", answerId=" + answerId +
            ", answerAnsw_A='" + answerAnsw_A + '\'' +
            ", answerAnsw_B='" + answerAnsw_B + '\'' +
            ", answerAnsw_C='" + answerAnsw_C + '\'' +
            ", answerAnsw_D='" + answerAnsw_D + '\'' +
            ", answerCorrect_ans='" + answerCorrect_ans + '\'' +
            '}';
  }
}
