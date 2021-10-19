package com.java.demo_ttcscn.enitities.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "result")
@EntityListeners(AuditingEntityListener.class)
public class ResultQuestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "username", unique = true, updatable = false)
  private String username;

  @Column(name = "code")
  private String codeQues;

  @Column(name = "name")
  private String name;

  @Column(name = "number_of_question")
  private int numberQuestion;

  @Column(name = "number_point")
  private float numberPoint;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCodeQues() {
    return codeQues;
  }

  public void setCodeQues(String codeQues) {
    this.codeQues = codeQues;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
