package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.TopicDto;
import com.java.demo_ttcscn.enitities.model.Topic;
import com.java.demo_ttcscn.exception.NotFoundException;
import com.java.demo_ttcscn.repositories.AnswerRepository;
import com.java.demo_ttcscn.repositories.CodeQuestionRepository;
import com.java.demo_ttcscn.repositories.LessonRepository;
import com.java.demo_ttcscn.repositories.TopicRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic, TopicDto> implements TopicService {
  @Autowired private TopicRepository topicRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired private LessonRepository lessionRepository;
  @Autowired private CodeQuestionRepository codeQuestionRepository;
  @Autowired private AnswerRepository answerRepository;

  @Override
  protected Class<Topic> classEntity() {
    return Topic.class;
  }

  @Override
  protected Class<TopicDto> classDto() {
    return TopicDto.class;
  }

  @Override
  protected BaseRepository<Topic> baseRepository() {
    return topicRepository;
  }

  @Override
  public List<TopicDto> getAllTopicByLessonId(int id) {
    try {
      return topicRepository.getTopicByLessonId(id).stream()
          .map(source -> modelMapper.map(source, classDto()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<TopicDto> getTopicByHot() {
    try {
      return topicRepository.getTopicByHot().stream()
          .map(source -> modelMapper.map(source, classDto()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<TopicDto> getAllTopic() {
    try {
      return topicRepository.findAll().stream()
          .map(source -> modelMapper.map(source, classDto()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<TopicDto> getTopicRandom(int limit) {
    List<TopicDto> rs =
        topicRepository.getTopicRandom(limit).stream()
            .map(source -> modelMapper.map(source, classDto()))
            .collect(Collectors.toList());
    if (rs.size() > 0) {
      return rs;
    } else throw new NotFoundException("Không đủ dữ liệu");
  }

  @Override
  public TopicDto handleUpadteOver(TopicDto updated) {
    try {
      if (updated.getImage().equals("")) {
        String image = topicRepository.findById(updated.getId()).get().getImage();
        updated.setImage(image);
      }
      Topic rs = modelMapper.map(updated, classEntity());
      rs.setLesson(lessionRepository.findById(updated.getLessonId()).get());
      topicRepository.save(rs);
      return updated;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String delete(int id) {
    Topic tp = topicRepository.findById(id).get();
    answerRepository.deleteByCodeTopic(tp.getName());
    codeQuestionRepository.deleteByTopic(tp.getName());
    return super.delete(id);
  }
}
