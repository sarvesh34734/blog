package com.blog.restapi.service.impl;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.entity.Post;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.repository.PostRepository;
import com.blog.restapi.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto dto) {
        // convert dto to entity
        Post p = new Post();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setContent(dto.getContent());
        // save
        Post savedPost = postRepository.save(p);

        return populate(savedPost);
    }

    @Override
    public List<PostDto> getPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> populate(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        return populate(post);
    }

    private PostDto populate(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }
}
