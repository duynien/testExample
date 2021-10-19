package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Comment;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment> {
}
