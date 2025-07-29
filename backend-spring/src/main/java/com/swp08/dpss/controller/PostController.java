package com.swp08.dpss.controller;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.ApiResponse; // Import ApiResponse
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.enums.PostStatus; // Import PostStatus enum
import com.swp08.dpss.service.interfaces.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal; // Import AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Assuming PostRequest needs validation

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN', 'MEMBER')") // Define who can create posts
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, createdPost, "Post created successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Anyone authenticated can try to view a post, service handles specifics
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        PostResponse post = postService.getPostById(id, userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, post, "Post retrieved successfully"));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()") // Anyone authenticated can get posts, service handles specifics
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponse> posts = postService.getAllPosts(userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, posts, "All posts retrieved successfully"));
    }

    @GetMapping("/filter")
    @PreAuthorize("isAuthenticated()") // Anyone authenticated can filter, service handles specifics
    public ResponseEntity<ApiResponse<List<PostResponse>>> filterPostsByStatus(
            @RequestParam PostStatus status,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponse> posts = postService.getPostsByStatus(status, userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, posts, "Posts filtered by status retrieved successfully"));
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()") // Anyone authenticated can search, service handles specifics
    public ResponseEntity<ApiResponse<List<PostResponse>>> searchPosts(
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponse> posts = postService.searchPosts(keyword, userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, posts, "Posts search results retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER', 'ADMIN')") // Only authorized roles can soft delete
    // Consider adding check if the current user is the author and can delete their own DRAFT posts
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Post soft-deleted successfully"));
    }
}