package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.dto.CommentDto;
import com.java.demo_ttcscn.enitities.model.Comment;
import com.java.demo_ttcscn.services.base.BaseService;
import com.java.demo_ttcscn.services.model.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseControllerImpl<Comment, CommentDto> {
  @Autowired private CommentService commentService;

  @Override
  protected BaseService<Comment, CommentDto> getService() {
    return commentService;
  }
}
