package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.dto.LessonDto;
import com.java.demo_ttcscn.enitities.model.Lesson;
import com.java.demo_ttcscn.services.base.BaseService;

import java.util.List;

public interface LessonService extends BaseService<Lesson, LessonDto> {
    List<LessonDto> getAllLesson();
}
