package com.swp08.dpss.service.interfaces;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.enums.PostStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request);

    void deletePost(Long id);
    PostResponse getPostById(Long id, String username); // Modified to include UserDetails for access control

    List<PostResponse> getAllPosts(String username); // Modified to include UserDetails for access control

    List<PostResponse> getPostsByStatus(PostStatus status, String username); // Renamed from getPostByStatus and added UserDetails

    List<PostResponse> searchPosts(String keyword, String username); // New method for searching

}