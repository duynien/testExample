package com.java.demo_ttcscn.enitities.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CodeQuestionDto implements Serializable {
  private int id;
  private String code;
  private String topic;
  private String description;
  private String create_by;
  private LocalDateTime createdAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreate_by() {
    return create_by;
  }

  public void setCreate_by(String create_by) {
    this.create_by = create_by;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  //  public int getLessonId() {
  //    return lessonId;
  //  }
  //
  //  public void setLessonId(int lessonId) {
  //    this.lessonId = lessonId;
  //  }
}
