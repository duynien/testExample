package com.java.demo_ttcscn.enitities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.demo_ttcscn.enitities.enum_value.Level;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "question")
@EntityListeners(AuditingEntityListener.class)
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name_question;

  private String level;

  @Column(updatable = false)
  @CreatedDate
  private LocalDateTime create_at;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updated_at;
  @JsonIgnore
  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "code_id",referencedColumnName = "id")
  private CodeQuestion codeQuestion;

  @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
  @JoinColumn(name = "answer_id", referencedColumnName = "id")
  private Answer answer;

  public Question() {}

  public Question(int id, String name_question, CodeQuestion codeQuestion, Answer answer) {
    this.id = id;
    this.name_question = name_question;
    this.codeQuestion = codeQuestion;
    this.answer = answer;
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

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public Answer getAnswer() {
    return answer;
  }

  public void setAnswer(Answer answer) {
    this.answer = answer;
  }

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

  public CodeQuestion getCodeQuestion() {
    return codeQuestion;
  }

  public void setCodeQuestion(CodeQuestion codeQuestion) {
    this.codeQuestion = codeQuestion;
  }

  public Answer getAnswerList() {
    return answer;
  }

  public void setAnswerList(Answer answerList) {
    this.answer = answerList;
  }

  @Override
  public String toString() {
    return "Question{" +
            "id=" + id +
            ", name_question='" + name_question + '\'' +
            ", level='" + level + '\'' +
            ", create_at=" + create_at +
            ", updated_at=" + updated_at +
            ", codeQuestion=" + codeQuestion +
            ", answer=" + answer +
            '}';
  }
}
