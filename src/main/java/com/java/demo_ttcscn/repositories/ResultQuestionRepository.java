package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.ResultQuestion;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultQuestionRepository extends BaseRepository<ResultQuestion> {
  @Query(value = "select * from result where username = :username", nativeQuery = true)
  List<ResultQuestion> getResultByUsername(@Param("username") String username);
  @Query(value = "select * from result where code = :code", nativeQuery = true)
  List<ResultQuestion> getResultByCodeQues(@Param("code") String code);
}
