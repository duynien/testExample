package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.CodeQuestionDto;
import com.java.demo_ttcscn.enitities.model.CodeQuestion;
import com.java.demo_ttcscn.repositories.AnswerRepository;
import com.java.demo_ttcscn.repositories.CodeQuestionRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.CodeQuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeQuestionServiceImpl extends BaseServiceImpl<CodeQuestion, CodeQuestionDto>
    implements CodeQuestionService {
  @Autowired ModelMapper modelMapper;
  @Autowired CodeQuestionRepository codeQuestionRepository;
  @Autowired
  AnswerRepository answerRepository;

  @Override
  protected Class<CodeQuestion> classEntity() {
    return CodeQuestion.class;
  }

  @Override
  protected Class<CodeQuestionDto> classDto() {
    return CodeQuestionDto.class;
  }

  @Override
  protected BaseRepository<CodeQuestion> baseRepository() {
    return codeQuestionRepository;
  }

  @Override
  public String getRandomCodeQuestionByTopic(String topic) {
    return codeQuestionRepository.getRandomCodeQuestionByTopic(topic);
  }

  @Override
  public List<CodeQuestionDto> getAllCodeQuestionByTopic(String topic) {
    try {
      return codeQuestionRepository.getAllCodeQuestionByTopic(topic).stream()
          .map(source -> modelMapper.map(source, classDto()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<String> getAllCode() {
    return codeQuestionRepository.getAllCode();
  }

  @Override
  public String delete(int id) {
    CodeQuestion codeQuestion = codeQuestionRepository.findById(id).get();
    answerRepository.deleteByCode(codeQuestion.getCode());
    return super.delete(id);
  }
}
