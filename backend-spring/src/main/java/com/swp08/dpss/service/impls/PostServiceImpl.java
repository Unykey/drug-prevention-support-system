package com.swp08.dpss.service.impls;

import com.swp08.dpss.dto.requests.PostRequest;
import com.swp08.dpss.dto.responses.PostResponse;
import com.swp08.dpss.entity.Post;
import com.swp08.dpss.entity.User;
import com.swp08.dpss.repository.PostRepository;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.service.interfaces.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return toDto(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
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