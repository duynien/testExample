package com.java.demo_ttcscn.enitities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
//@Immutable
@Table(name = "answer")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String answ_A;
  private String answ_B;
  private String answ_C;
  private String answ_D;
  private String correct_ans;

  public Answer() {}

  public Answer(
      int id,
      String answ_A,
      String answ_B,
      String answ_C,
      String answ_D,
      String correct_ans) {
    this.id = id;
    this.answ_A = answ_A;
    this.answ_B = answ_B;
    this.answ_C = answ_C;
    this.answ_D = answ_D;
    this.correct_ans = correct_ans;
  }

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

  @Override
  public String toString() {
    return "Answer{" +
            "id=" + id +
            ", answ_A='" + answ_A + '\'' +
            ", answ_B='" + answ_B + '\'' +
            ", answ_C='" + answ_C + '\'' +
            ", answ_D='" + answ_D + '\'' +
            ", correct_ans='" + correct_ans + '\'' +
            '}';
  }
}
