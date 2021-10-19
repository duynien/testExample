package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.TopicDto;
import com.java.demo_ttcscn.enitities.model.Topic;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("topic")
public class TopicController extends BaseControllerImpl<Topic, TopicDto> {
  @Autowired ModelMapper modelMapper;
  @Autowired private TopicService topicService;

  @Override
  protected BaseService<Topic, TopicDto> getService() {
    return topicService;
  }

  @GetMapping("/topic-by-id")
  public ResponseEntity<?> getTopicByLessonId(@RequestParam int id) {
    return ResponseEntity.ok(topicService.getAllTopicByLessonId(id));
  }

  @Override
  public ResponseEntity<?> update(TopicDto updated) {
    return ResponseEntity.ok(topicService.handleUpadteOver(updated));
  }

  @GetMapping("/get-hot")
  public ResponseEntity<?> getTopicByHot() {
    return ResponseEntity.ok(topicService.getTopicByHot());
  }

  @GetMapping("/get-all")
  public ResponseEntity<?> getAllTopic() {
    return ResponseEntity.ok(topicService.getAllTopic());
  }

  @GetMapping("/get-limit")
  public ResponseEntity<?> getTopicRandom(@RequestParam int limit) {
    return ResponseEntity.ok(topicService.getTopicRandom(limit));
  }
}
