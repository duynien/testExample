package com.java.demo_ttcscn.services.model.impl;

import com.java.demo_ttcscn.enitities.dto.CommentDto;
import com.java.demo_ttcscn.enitities.model.Comment;
import com.java.demo_ttcscn.repositories.CommentRepository;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import com.java.demo_ttcscn.services.base.BaseServiceImpl;
import com.java.demo_ttcscn.services.model.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, CommentDto>
    implements CommentService {
  @Autowired private CommentRepository commentRepository;

  @Override
  protected Class<Comment> classEntity() {
    return Comment.class;
  }

  @Override
  protected Class<CommentDto> classDto() {
    return CommentDto.class;
  }

  @Override
  protected BaseRepository<Comment> baseRepository() {
    return commentRepository;
  }
}
