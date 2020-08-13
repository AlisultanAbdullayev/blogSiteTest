package com.alijaver.blogSite.repos;

import com.alijaver.blogSite.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository <Post, Integer>{

    List<Post> findByAuthor(String author);
}
