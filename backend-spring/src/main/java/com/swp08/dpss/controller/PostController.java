package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.ApiResponse;
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.enums.PostStatus;
import com.swp08.dpss.service.interfaces.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, createdPost, "Post created successfully"));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal String username) {
        List<PostResponse> posts;
        if (keyword != null && !keyword.isBlank()) {
            posts = postService.searchPosts(keyword, username);
            return ResponseEntity.ok(new ApiResponse<>(true, posts, "Posts search results retrieved successfully"));
        } else if (status != null) {
            posts = postService.getPostsByStatus(status, username);
            return ResponseEntity.ok(new ApiResponse<>(true, posts, "Posts filtered by status retrieved successfully"));
        } else {
            posts = postService.getAllPosts(username);
            return ResponseEntity.ok(new ApiResponse<>(true, posts, "All posts retrieved successfully"));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id,
                                                                 @AuthenticationPrincipal String username) {
        PostResponse post = postService.getPostById(id, username);
        return ResponseEntity.ok(new ApiResponse<>(true, post, "Post retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Post soft-deleted successfully"));
    }
}