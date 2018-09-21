package com.sit.cloudnative.PostService.Comment;

import java.util.List;

import javax.validation.Valid;

import com.sit.cloudnative.PostService.Post.PostRepository;
import com.sit.cloudnative.PostService.User.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    // // Get All Comment
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comment = commentService.getAllComments();
        return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
    }

    // // Create a new Comment
    @PostMapping("/posts/{postId}/{userId}/comments")
    public Comment createComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "userId") Long userId, @Valid @RequestBody Comment comment) {
        try {
            return postRepository.findById(postId).map(post -> {
                comment.setPost(post);
                return commentRepository.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
        } finally {
            return userRepository.findById(userId).map(user -> {
                comment.setUser(user);
                return commentRepository.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
        }

    }

    // // Get Comment By PostId

    @GetMapping("/comment/{postId}/Postcomments")
    public ResponseEntity<List<Comment>> getAllCommentByPostId(@PathVariable(value = "postId") Long postId) {

        List<Comment> comment = commentRepository.findByPostId(postId);
        return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);

    }
    // // Get Comment By UserId

    @GetMapping("/comment/{userId}/Usercomments")
    public ResponseEntity<List<Comment>> getAllCommentByUserId(@PathVariable(value = "userId") Long userId) {
        List<Comment> comment = commentRepository.findByUserId(userId);
        return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
    }

    // // Get a Single Comment
    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommetById(@PathVariable(value = "id") Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);

    }
}