package org.linkedinclone.postsservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.linkedinclone.postsservice.entity.PostLike;
import org.linkedinclone.postsservice.exception.ResourceNotFoundException;
import org.linkedinclone.postsservice.exception.BadRequestException;
import org.linkedinclone.postsservice.repository.PostLikeRepository;
import org.linkedinclone.postsservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private static final Logger log = LoggerFactory.getLogger(PostLikeService.class);
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with id {} liking the post with id {}", userId, postId);

        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+ postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(hasAlreadyLiked) throw new BadRequestException("You cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

//        TODO: send notification to owner of the post ~ Kafka
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with id {} unliking the post with id {}", userId, postId);

        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post you have not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
