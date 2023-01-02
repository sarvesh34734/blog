package com.blog.restapi.service;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.dtos.PostResponse;

public interface PostService {

    PostDto createPost(PostDto dto);

    PostResponse getPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPost(Long id);

    PostDto updatePost(PostDto dto,Long id);

    void deletePostById(Long id);

}
