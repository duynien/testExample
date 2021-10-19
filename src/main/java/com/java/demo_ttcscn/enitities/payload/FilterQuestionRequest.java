package com.java.demo_ttcscn.enitities.payload;

public class FilterQuestionRequest {
  private String code;
  private int easy;
  private int normal;
  private int difficult;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getEasy() {
    return easy;
  }

  public void setEasy(int easy) {
    this.easy = easy;
  }

  public int getNormal() {
    return normal;
  }

  public void setNormal(int normal) {
    this.normal = normal;
  }

  public int getDifficult() {
    return difficult;
  }

  public void setDifficult(int difficult) {
    this.difficult = difficult;
  }

  @Override
  public String toString() {
    return "FilterQuestionRequest{"
        + "code='"
        + code
        + '\''
        + ", easy="
        + easy
        + ", normal="
        + normal
        + ", difficult="
        + difficult
        + '}';
  }
}
