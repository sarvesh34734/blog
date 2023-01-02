package com.blog.restapi.service;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.dtos.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto dto);

    PostResponse getPosts(int pageNo, int pageSize);

    PostDto getPost(Long id);

    PostDto updatePost(PostDto dto,Long id);

    void deletePostById(Long id);

}
