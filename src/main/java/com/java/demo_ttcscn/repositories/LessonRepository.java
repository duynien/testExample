package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Lesson;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends BaseRepository<Lesson> {
    @Query(value = "select * from #{#entityName} where ishot = 1 order by rand()",nativeQuery = true)
    List<Lesson> getByHot();
}
