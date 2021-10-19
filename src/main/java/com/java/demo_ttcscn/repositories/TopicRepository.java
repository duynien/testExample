package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Topic;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends BaseRepository<Topic> {
    @Query(value = "select * from #{#entityName} where lesson_id = :id",nativeQuery = true)
    List<Topic> getTopicByLessonId(@Param("id") int id);
    @Query(value = "select * from #{#entityName} where ishot = 1",nativeQuery = true)
    List<Topic> getTopicByHot();
    @Query(value = "select * from #{#entityName} order by rand() limit :limit",nativeQuery = true)
    List<Topic> getTopicRandom(@Param("limit") int limit);
}
