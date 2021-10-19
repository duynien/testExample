package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.LessonDto;
import com.java.demo_ttcscn.enitities.model.Lesson;
import com.java.demo_ttcscn.repositories.LessonRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson, LessonDto> implements LessonService {
  @Autowired private LessonRepository lessionRepository;
  @Autowired private ModelMapper modelMapper;

  @Override
  protected Class<Lesson> classEntity() {
    return Lesson.class;
  }

  @Override
  protected Class<LessonDto> classDto() {
    return LessonDto.class;
  }

  @Override
  protected BaseRepository<Lesson> baseRepository() {
    return lessionRepository;
  }

//  @Override
//  public List<LessonDto> getLesstionByHot() {
//    List<Lesson> lessons = lessionRepository.getByHot();
//    List<LessonDto> rs =
//        lessons.stream()
//            .map(source -> modelMapper.map(source, classDto()))
//            .collect(Collectors.toList());
//    return rs;
//  }

  @Override
  public List<LessonDto> getAllLesson() {
    try {
      return lessionRepository.findAll().stream()
          .map(source -> modelMapper.map(source, classDto()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
