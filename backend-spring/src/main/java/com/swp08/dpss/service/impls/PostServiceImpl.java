package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.entity.Post;
import com.swp08.dpss.entity.client.User;
import com.swp08.dpss.enums.PostStatus;
import com.swp08.dpss.enums.Roles;
import com.swp08.dpss.repository.PostRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.PostService;
import com.swp08.dpss.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // Keep for fetching author
    private final UserService userService; // Use UserService to get user details from UserDetails

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public PostResponse createPost(PostRequest request) {
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(author);

        Post saved = postRepository.save(post);
        return toDto(saved);
    }

    @Override
    public PostResponse getPostById(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + id));

        User currentUser = userService.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        // Access control logic for getting a single post
        // Admins/Managers/Staff can see any post
        if (currentUser.getRole() == Roles.ADMIN || currentUser.getRole() == Roles.MANAGER || currentUser.getRole() == Roles.STAFF) {
            return toDto(post);
        }
        // Users (including authors) can see PUBLISHED posts or their own DRAFT/PENDING posts
        else if (post.getStatus() == PostStatus.PUBLISHED ||
                (post.getAuthor().getId().equals(currentUser.getId()) &&
                        (post.getStatus() == PostStatus.DRAFT))) {
            return toDto(post);
        } else {
            throw new org.springframework.security.access.AccessDeniedException("You do not have permission to view this post.");
        }
    }

    @Override
    public List<PostResponse> getAllPosts(String username) {
        User currentUser = userService.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        if (currentUser.getRole() == Roles.ADMIN || currentUser.getRole() == Roles.MANAGER || currentUser.getRole() == Roles.STAFF) {
            // Admins/Managers/Staff can see all non-deleted posts
            return postRepository.findByStatusIsNot(PostStatus.DELETED)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } else {
            // Members can only see PUBLISHED posts
            return postRepository.findByStatus(PostStatus.PUBLISHED)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<PostResponse> getPostsByStatus(PostStatus status, String username) {
        User currentUser = userService.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        if (currentUser.getRole() == Roles.ADMIN || currentUser.getRole() == Roles.MANAGER || currentUser.getRole() == Roles.STAFF) {
            // Admins/Managers/Staff can filter by any status (except DELETED, handled by other methods if needed)
            return postRepository.findByStatus(status)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } else {
            // Members can only see PUBLISHED posts, regardless of the requested status
            if (status != PostStatus.PUBLISHED) {
                // If a member requests a status other than PUBLISHED, return empty or throw forbidden
                return List.of(); // Or throw new AccessDeniedException("Members can only filter by PUBLISHED status.");
            }
            return postRepository.findByStatus(PostStatus.PUBLISHED)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<PostResponse> searchPosts(String keyword, String username) {
        User currentUser = userService.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        if (keyword == null || keyword.isBlank()) {
            // If keyword is empty, return all accessible posts
            return getAllPosts(username);
        }

        // Search in title or content for non-deleted posts
        List<Post> foundPosts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatusIsNot(
                keyword, keyword, PostStatus.DELETED
        );

        // Filter based on user role (similar to getAllPosts)
        if (currentUser.getRole() == Roles.ADMIN || currentUser.getRole() == Roles.MANAGER || currentUser.getRole() == Roles.STAFF) {
            return foundPosts.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } else {
            // Members can only search within PUBLISHED posts
            return foundPosts.stream()
                    .filter(post -> post.getStatus() == PostStatus.PUBLISHED)
                    .map(this::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setStatus(PostStatus.DELETED);
        postRepository.save(post);
    }

    private PostResponse toDto(Post post) {
        PostResponse dto = new PostResponse();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setStatus(post.getStatus());
        dto.setAuthorName(post.getAuthor().getName());
        return dto;
    }
}