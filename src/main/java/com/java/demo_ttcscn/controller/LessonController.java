package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.LessonDto;
import com.java.demo_ttcscn.enitities.model.Lesson;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/lesson")
public class LessonController extends BaseControllerImpl<Lesson, LessonDto> {
  @Autowired private LessonService lessonService;

  @Override
  protected BaseService<Lesson, LessonDto> getService() {
    return lessonService;
  }

  @Override
  public ResponseEntity<?> update(LessonDto updated) {
    try{
      if (updated.getImage().equals("")){
        String image = lessonService.getById(updated.getId()).getImage();
        updated.setImage(image);
      }
      return ResponseEntity.ok(lessonService.update(updated));
    }catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }

  @GetMapping("/get-all")
  public ResponseEntity<?> getAll(){
    return ResponseEntity.ok(lessonService.getAllLesson());
  }
//  @GetMapping("/get-hot")
//  public ResponseEntity<?> getHot() {
//    return ResponseEntity.ok(lessonService.getLesstionByHot());
//  }
}
