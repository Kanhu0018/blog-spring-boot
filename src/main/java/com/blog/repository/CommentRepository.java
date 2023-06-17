package com.blog.repository;

import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findByPostId(long id);

    /*   //select * from Comment where post_id=2
         List<Comment> findByEmail(String email);
         List<Comment> findByName(String name);
         List<Comment> findByMobile(long mobile);
         */
}
