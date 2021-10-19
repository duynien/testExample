package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.Post;
import com.java.demo_ttcscn.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post> {
}
