package org.linkedinclone.postsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.linkedinclone.postsservice.dto.PostCreateRequestDto;
import org.linkedinclone.postsservice.dto.PostDto;
import org.linkedinclone.postsservice.entity.Post;
import org.linkedinclone.postsservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post for user with ID: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("getting post with id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(new ResourceNotFoundException("Post not found with id: {}", postId));
        return modelMapper.map(post, PostDto.class);
    }
}
