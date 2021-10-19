package com.java.demo_ttcscn.enitities.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "q_and_a")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Ask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String purpose;

  @Column(name = "content_question")
  private String content;

  @LastModifiedDate
  @Column(name = "create_at",updatable = false)
  private LocalDateTime createAt;

  @Column(updatable = false)
  private String username;

  @OneToMany(mappedBy = "ask", cascade = CascadeType.MERGE)
  private List<Comment> commentList;
}
