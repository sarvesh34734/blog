package com.blog.restapi.controller;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/posts")
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping(value="/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post){
        PostDto dto = postService.createPost(post);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(){
        return ResponseEntity.ok(postService.getPosts());
    }

}