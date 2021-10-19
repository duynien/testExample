package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.model.CodeQuestion;
import com.java.demo_ttcscn.enitities.dto.CodeQuestionDto;
import com.java.demo_ttcscn.services.base.BaseService;

import java.util.List;

public interface CodeQuestionService extends BaseService<CodeQuestion, CodeQuestionDto> {
    public String getRandomCodeQuestionByTopic(String topic);
    public List<CodeQuestionDto> getAllCodeQuestionByTopic(String topic);
    public List<String> getAllCode();
//    void deleteByTopic(String topic);
}
