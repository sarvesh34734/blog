package com.blog.restapi.service.impl;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.dtos.PostResponse;
import com.blog.restapi.entity.Post;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.repository.PostRepository;
import com.blog.restapi.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

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
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir){

        // sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.ofSize(pageSize).withPage(pageNo).withSort(sort);

        Page<Post> page = postRepository.findAll(pageable);

        // get content from page
        List<Post> posts = page.getContent();
        List<PostDto> content = posts.stream().map(this::populate).toList();
        PostResponse res = PostResponse.builder()
                .content(content)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .last(page.isLast())
                .build();
        return res;
    }

    @Override
    public PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        return populate(post);
    }

    @Override
    public PostDto updatePost(PostDto dto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        post.setContent(dto.getContent());
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        postRepository.save(post);
        return populate(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
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
