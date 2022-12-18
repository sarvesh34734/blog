package com.blog.restapi.service;

import com.blog.restapi.dtos.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto dto);

    List<PostDto> getPosts();

    PostDto getPost(Long id);

    PostDto updatePost(PostDto dto,Long id);

    void deletePostById(Long id);

}
