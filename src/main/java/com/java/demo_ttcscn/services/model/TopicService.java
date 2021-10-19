package com.java.demo_ttcscn.services.model;

import com.java.demo_ttcscn.enitities.dto.TopicDto;
import com.java.demo_ttcscn.enitities.model.Topic;
import com.java.demo_ttcscn.services.base.BaseService;

import java.util.List;

public interface TopicService extends BaseService<Topic, TopicDto> {
    List<TopicDto> getAllTopicByLessonId(int id);
    List<TopicDto> getTopicByHot();
    List<TopicDto> getAllTopic();
    List<TopicDto> getTopicRandom(int limit);
    TopicDto handleUpadteOver(TopicDto updated);
}
