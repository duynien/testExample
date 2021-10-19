package com.java.demo_ttcscn.controller;

import com.java.demo_ttcscn.enitities.model.Post;
import com.java.demo_ttcscn.services.model.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired private PostService postService;
  @GetMapping
  public ResponseEntity<?> getPost(){
    return ResponseEntity.ok(postService.getPost());
  }
  @PutMapping
  public ResponseEntity<?> updatePost(@ModelAttribute Post post){
    if(post.getImage().equals("")){
      String image = postService.getPost().getImage();
      post.setImage(image);
    }
    return ResponseEntity.ok(postService.updatePost(post));
  }
}
