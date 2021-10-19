package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Answer;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnswerRepository extends BaseRepository<Answer> {
  @Query(
      value = "select a.* from answer a join question q on a.id = q.answer_id where q.id =:codeid",
      nativeQuery = true)
  Answer getAnswerByQuestion(@Param("codeid") int codeQuestion);

  @Query(
      value =
          "select q.name_question from answer a join question q on a.id = q.answer_id where q.id =:codeid",
      nativeQuery = true)
  String getNameByQuestion(@Param("codeid") int codeQuestion);

  @Override
  @Transactional
  @Modifying
  @Query(value = "delete from #{#entityName} where id = :id", nativeQuery = true)
  void deleteById(@Param("id") Object id);

  @Transactional
  @Modifying
  @Query(
      value =
          "DELETE a FROM answer a JOIN question q ON q.answer_id = a.id JOIN code_ques c ON q.code_id = c.id " +
                  "WHERE c.topic = :topic",
      nativeQuery = true)
  void deleteByCodeTopic(@Param("topic") String topic);
  @Transactional
  @Modifying
  @Query(
          value =
                  "DELETE a FROM answer a JOIN question q ON q.answer_id = a.id JOIN code_ques c ON q.code_id = c.id " +
                          "WHERE c.code = :code",
          nativeQuery = true)
  void deleteByCode(@Param("code") String code);
}
