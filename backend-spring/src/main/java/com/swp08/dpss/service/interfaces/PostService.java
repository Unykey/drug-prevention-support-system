package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.enums.PostStatus;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request);
    PostResponse getPostById(Long id);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostByStatus(PostStatus status);
    void deletePost(Long id);
}