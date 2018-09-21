package com.sit.cloudnative.PostService.Post;

import java.util.List;

import javax.validation.Valid;

import com.sit.cloudnative.PostService.User.UserRepository;
import com.sit.cloudnative.PostService.User.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    // // Get All Post
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> post = postService.getAllPosts();
        return new ResponseEntity<List<Post>>(post, HttpStatus.OK);
    }

    // Create a new Post
    @PostMapping("/users/{userId}/posts")
    public Post createPost(@PathVariable(value = "userId") Long userId, @Valid @RequestBody Post post) {
        return userRepository.findById(userId).map(user -> {
            post.setUser(user);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }

    // Get Post By UserId
    @GetMapping("/post/{userId}/posts")
    public ResponseEntity<List<Post>> getAllPostsByUserId(@PathVariable(value = "userId") Long userId) {
        List<Post> post = postRepository.findByUserId(userId);
        return new ResponseEntity<List<Post>>(post, HttpStatus.OK);

    }

    // Get a Single Post
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long postId) {
        Post post = postService.getPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);

    }

}
