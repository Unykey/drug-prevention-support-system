package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatus(int status);
    List<Post> findByAuthorId(Long authorId);
}