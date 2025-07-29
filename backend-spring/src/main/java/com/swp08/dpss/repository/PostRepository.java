package com.swp08.dpss.repository;

import com.swp08.dpss.entity.Post;
import com.swp08.dpss.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatusIsNot(PostStatus status);

    List<Post> findByStatus(PostStatus status);

    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatusIsNot(String keyword, String keyword1, PostStatus postStatus);
}