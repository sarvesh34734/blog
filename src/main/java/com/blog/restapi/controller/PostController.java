package com.blog.restapi.controller;

import com.blog.restapi.dtos.PostDto;
import com.blog.restapi.dtos.PostResponse;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.service.PostService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/posts")
public class PostController {

    Logger LOG = LoggerFactory.getLogger(PostController.class);

    @Resource
    private PostService postService;

    @PostMapping(value="/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post){
        PostDto dto = postService.createPost(post);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(@RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
                                                 @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
                                                 @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                                 @RequestParam(value="sortDir", defaultValue = "asc",required = false) String sortDir){
        return ResponseEntity.ok(postService.getPosts(pageNo,pageSize,sortBy,sortDir));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        try{
            return ResponseEntity.ok(postService.getPost(id));
        }
        catch(ResourceNotFoundException ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto dto,@PathVariable Long id){
        try{
            return ResponseEntity.ok(postService.updatePost(dto,id));
        }
        catch(ResourceNotFoundException ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        try{
            postService.deletePostById(id);
            return ResponseEntity.ok("Success");
        }
        catch(Exception ex){
            LOG.error("failed to delete post by id : {}",id);
            return new ResponseEntity<>("post with id " +  id + " not found",HttpStatus.NOT_FOUND);
        }
    }

}
