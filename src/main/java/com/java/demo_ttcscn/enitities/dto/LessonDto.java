package com.java.demo_ttcscn.enitities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LessonDto implements Serializable {
  private int id;
  private String title;
  private String description;
  @CreatedDate private LocalDateTime create_at;
  private String createBy;
  private Boolean ishot;
  private String image;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreate_at() {
    return create_at;
  }

  public void setCreate_at(LocalDateTime create_at) {
    this.create_at = create_at;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Boolean getIshot() {
    return ishot;
  }

  public Boolean isIshot() {
    return ishot;
  }

  public void setIshot(Boolean ishot) {
    this.ishot = ishot;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
