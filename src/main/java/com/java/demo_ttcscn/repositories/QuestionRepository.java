package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Question;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends BaseRepository<Question> {
  @Query(
      value =
          "SELECT q.* FROM question q join code_ques c on q.code_id=c.id "
              + "where c.code = :code",
      nativeQuery = true)
  List<Question> getQuestionByCodeAndPage(@Param("code") String code, Pageable pageable);

  @Query(
      value =
          "SELECT count(*) FROM question q join code_ques c on q.code_id=c.id "
              + "where c.code = :code",
      nativeQuery = true)
  int getTotalRecordByCode(@Param("code") String code);
  @Query(
          value =
                  "SELECT count(*) FROM question q where q.code_id = :code",
          nativeQuery = true)
  int getTotalElement(@Param("code") String code);
  @Query(
      value =
          "SELECT q.* FROM question q join code_ques c on q.code_id = c.id "
              + "where c.code = :code and q.level = :level order by rand() limit :limit",
      nativeQuery = true)
  List<Question> getQuestionByLevelAndCode(
      @Param("level") String level, @Param("code") String code, @Param("limit") int limit);

  @Query(
      value =
          "SELECT q FROM Question q join CodeQuestion c on q.codeQuestion.id = c.id where c.code = :code")
  Page<Question> getFilterByCodeQuesByAdmin(Pageable pageable, @Param("code") String code);

  @Query(
      value =
          "SELECT q FROM Question q join CodeQuestion c on q.codeQuestion.id = c.id where c.code = :code")
  Page<Question> getFilterByCodeQuesByUser(Pageable pageable, @Param("code") String code);
}
