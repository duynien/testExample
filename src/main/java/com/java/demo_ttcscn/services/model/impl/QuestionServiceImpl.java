package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.QuestionDto;
import com.java.demo_ttcscn.enitities.dto.QuestionForExamDto;
import com.java.demo_ttcscn.enitities.dto.QuestionForFilterByUserDto;
import com.java.demo_ttcscn.enitities.enumValue.Level;
import com.java.demo_ttcscn.enitities.model.Question;
import com.java.demo_ttcscn.enitities.payload.FilterQuestionRequest;
import com.java.demo_ttcscn.enitities.result.PageResponse;
import com.java.demo_ttcscn.enitities.result.QuestionResponse;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.AnswerRepository;
import com.java.demo_ttcscn.repositories.CodeQuestionRepository;
import com.java.demo_ttcscn.repositories.QuestionRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, QuestionDto>
    implements QuestionService {
  @Autowired ModelMapper modelMapper;
  @Autowired private QuestionRepository questionRepository;
  @Autowired private AnswerRepository answerRepository;
  @Autowired private CodeQuestionRepository codeQuestionRepository;

  @Override
  protected Class<Question> classEntity() {
    return Question.class;
  }

  @Override
  protected Class<QuestionDto> classDto() {
    return QuestionDto.class;
  }

  private Class<QuestionForFilterByUserDto> classQuestionDtoForFilterByUser() {
    return QuestionForFilterByUserDto.class;
  }

  @Override
  protected BaseRepository<Question> baseRepository() {
    return questionRepository;
  }

  @Override
  public QuestionResponse getmQuestionByCodeAndPage(String code, int page) {
    //    Sort sort = Sort.by("name").descending();
    try {
      Pageable pageable = PageRequest.of(page - 1, 20);
      List<Question> listQ = questionRepository.getQuestionByCodeAndPage(code, pageable);
      int time = (int) Math.ceil((listQ.size() * 2) / 3);
      List<QuestionForExamDto> rs =
          listQ.stream()
              .map(source -> modelMapper.map(source, QuestionForExamDto.class))
              .collect(Collectors.toList());
      String topic = codeQuestionRepository.getTopicByCode(code);
      return new QuestionResponse(topic, code, time, rs);
    } catch (Exception e) {
      throw new NotFoundException("Không tìm thấy dữ liệu");
    }
  }

  @Override
  public int totalRecordRenderPage(String code) {
    int limit = 20;
    int totalRecord = questionRepository.getTotalRecordByCode(code);
    int totalPage = (int) Math.ceil((double) totalRecord / (double) limit);
    return totalPage;
  }

  @Override
  public int totalElement(String code) {
    return questionRepository.getTotalElement(code);
  }

  @Override
  public List<Level> getLevels() {
    List<Level> levelList = Arrays.asList(Level.values());
    return levelList;
  }

  @Override
  public QuestionResponse getQuestionByLevelAndCode(FilterQuestionRequest filterQuestionRequest) {
    List<Question> questions = new ArrayList<>();
    Map<Level, Integer> questionMap = new HashMap<>();
    questionMap.put(Level.easy, filterQuestionRequest.getEasy());
    questionMap.put(Level.normal, filterQuestionRequest.getNormal());
    questionMap.put(Level.difficult, filterQuestionRequest.getDifficult());
    Set<Level> keySet = questionMap.keySet();
    List<Question> resultFilter;
    for (Level level : keySet) {
      resultFilter =
          questionRepository.getQuestionByLevelAndCode(
              level.toString(), filterQuestionRequest.getCode(), questionMap.get(level));
      resultFilter.stream()
          .forEach(
              source -> {
                questions.add(source);
              });
    }
    List<QuestionDto> rs =
        questions.stream()
            .map(source -> modelMapper.map(source, classDto()))
            .collect(Collectors.toList());
    int size = rs.size();
    int time = (int) Math.ceil(((double) size * 2) / 3);
    String code = filterQuestionRequest.getCode();
    String topic = codeQuestionRepository.getTopicByCode(code);
    return new QuestionResponse(topic, code, time, rs);
  }

  public QuestionResponse handleQuestionByModalFilter(FilterQuestionRequest filterQuestionRequest) {
    List<Question> questions = new ArrayList<>();
    Map<Level, Integer> questionMap = new HashMap<>();
    questionMap.put(Level.easy, filterQuestionRequest.getEasy());
    questionMap.put(Level.normal, filterQuestionRequest.getNormal());
    questionMap.put(Level.difficult, filterQuestionRequest.getDifficult());
    int totalQuestionRequest =
        filterQuestionRequest.getEasy()
            + filterQuestionRequest.getNormal()
            + filterQuestionRequest.getDifficult();
    Set<Level> keySet = questionMap.keySet();
    List<Question> resultFilter;
    for (Level level : keySet) {
      resultFilter =
          questionRepository.getQuestionByLevelAndCode(
              level.toString(), filterQuestionRequest.getCode(), questionMap.get(level));
      resultFilter.stream()
          .forEach(
              source -> {
                questions.add(source);
              });
    }
    List<QuestionForExamDto> rs =
        questions.stream()
            .map(source -> modelMapper.map(source, QuestionForExamDto.class))
            .collect(Collectors.toList());
    int size = rs.size();
    if (size == totalQuestionRequest) {
      String code = filterQuestionRequest.getCode();
      String topic = codeQuestionRepository.getTopicByCode(code);
      int time = (int) Math.ceil(((double) size * 2) / 3);
      return new QuestionResponse(topic, code, time, rs);
    } else {
      return new QuestionResponse();
    }
  }

  @Override
  public PageResponse getFilterByCodeQuestionByAdmin(Pageable pageable, String code) {
    Page<Question> page = questionRepository.getFilterByCodeQuesByAdmin(pageable, code);
    int totalPage = page.getTotalPages();
    List<QuestionDto> res =
        page.getContent().stream()
            .map(source -> modelMapper.map(source, classDto()))
            .collect(Collectors.toList());
    return new PageResponse(
        pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
  }

  @Override
  public PageResponse getFilterByCodeQuestionByUser(Pageable pageable, String code) {
    try {
      Page<Question> page = questionRepository.getFilterByCodeQuesByUser(pageable, code);
      int totalPage = page.getTotalPages();
      List<QuestionForFilterByUserDto> res =
          page.getContent().stream()
              .map(source -> modelMapper.map(source, classQuestionDtoForFilterByUser()))
              .collect(Collectors.toList());
      return new PageResponse(
          pageable.getPageNumber(), totalPage, (int) page.getTotalElements(), res);
    } catch (Exception e) {
      e.printStackTrace();
      throw new NotFoundException("Không có dữ liệu");
    }
  }

  @Override
  public QuestionResponse handleQuestionBySelectByUser(String message) {
    try {
      String code = message.split("\\$")[0];
      String idQues[] = message.split("\\$")[1].split("&");
      List<Question> listQ = new ArrayList<>();
      for (String id : idQues) {
        listQ.add(questionRepository.findById(Integer.parseInt(id)).get());
      }
      List<QuestionForExamDto> rs =
          listQ.stream()
              .map(source -> modelMapper.map(source, QuestionForExamDto.class))
              .collect(Collectors.toList());
      int time = (int) Math.ceil(((double) rs.size() * 2) / 3);
      String topic = codeQuestionRepository.getTopicByCode(code);
      return new QuestionResponse(topic, code, time, rs);
    } catch (Exception e) {
      e.printStackTrace();
      return new QuestionResponse();
    }
  }

  @Override
  public String delete(int id) {
    try {
      Question question = questionRepository.findById(id).get();
      answerRepository.deleteById(question.getAnswer().getId());
      return "Xóa thành công";
    } catch (Exception e) {
      e.printStackTrace();
      return "Có lỗi xảy ra";
    }
  }
}
