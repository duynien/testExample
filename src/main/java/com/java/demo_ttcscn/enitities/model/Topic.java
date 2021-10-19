package com.java.demo_ttcscn.enitities.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "topic")
@AllArgsConstructor
@NoArgsConstructor
public class Topic{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true)
  private String name;
  private boolean ishot;

  @Column(name = "create_by", updatable = false)
  private String createBy;

  private String image;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "lesson_id",referencedColumnName = "id")
  private Lesson lesson;

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

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  @Override
  public String toString() {
    return "Topic{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", ishot="
        + ishot
        + ", createBy='"
        + createBy
        + '\''
        + ", image='"
        + image
        + '\''
        + ", lessonTopic="
        + lesson
        + '}';
  }
}
