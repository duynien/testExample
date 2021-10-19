package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.CodeQuestion;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CodeQuestionRepository extends BaseRepository<CodeQuestion> {
  @Query(
      value = "select code from code_ques where topic = :topic order by rand() limit 1",
      nativeQuery = true)
  String getRandomCodeQuestionByTopic(@Param("topic") String topic);

  @Query(value = "select * from code_ques where topic = :topic", nativeQuery = true)
  List<CodeQuestion> getAllCodeQuestionByTopic(@Param("topic") String topic);

  @Query(value = "select code from code_ques", nativeQuery = true)
  List<String> getAllCode();

  @Query(value = "select topic from code_ques where code=:code", nativeQuery = true)
  String getTopicByCode(@Param("code") String code);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM code_ques WHERE topic=:topic", nativeQuery = true)
  void deleteByTopic(@Param("topic") String topic);
}
