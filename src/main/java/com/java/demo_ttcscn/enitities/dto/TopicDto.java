package com.java.demo_ttcscn.enitities.dto;
// duyniencommit
// duyniencommit2
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class TopicDto implements Serializable {
  private int id;
  private String name;
  private boolean ishot;
  private String createBy;
  private String image;
  private int lessonId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isIshot() {
    return ishot;
  }

  public void setIshot(boolean ishot) {
    this.ishot = ishot;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getLessonId() {
    return lessonId;
  }

  public void setLessonId(int lessonId) {
    this.lessonId = lessonId;
  }
}
