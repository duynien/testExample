package com.java.demo_ttcscn.enitities.result;

public class QuestionResponse {
  private String topic;
  private String code;
  private int time;
  private Object content;

  public QuestionResponse() {}

  public QuestionResponse(String topic, String code, int time, Object content) {
    this.topic = topic;
    this.code = code;
    this.time = time;
    this.content = content;
  }

  public String getTopic() {
    return topic;
  }

  public void getTopic(String course) {
    this.topic = course;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public Object getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = content;
  }
}
