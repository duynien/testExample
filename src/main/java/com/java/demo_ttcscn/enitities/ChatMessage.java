package com.java.demo_ttcscn.enitities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ChatMessage {
  private String content;
  private String sender;
  private LocalDateTime time;
  private MessageType type;

  public enum MessageType {
    DOEXAM,
    LEAVE,
    JOIN,
    RELOAD
  }
}
