package com.venkateshng.my_profile_site_backend.controller;

import com.venkateshng.my_profile_site_backend.model.Post;
import com.venkateshng.my_profile_site_backend.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST controller, handling HTTP requests
@RequestMapping("/api/posts") // Base path for all endpoints in this controller (e.g., /api/posts)
@CrossOrigin(origins = "${app.cors.allowed-origins}") // Allows requests from specified origins (your frontend)
public class PostController {

    private final PostRepository postRepository;

    // Constructor injection: Spring automatically provides an instance of
    // PostRepository
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // GET /api/posts: Returns all published posts, ordered by creation date (newest
    // first)
    @GetMapping
    public List<Post> getAllPublishedPosts() {
        return postRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    // GET /api/posts/{slug}: Returns a single post by its unique slug
    @GetMapping("/{slug}")
    public ResponseEntity<Post> getPostBySlug(@PathVariable String slug) {
        Optional<Post> post = postRepository.findBySlug(slug);
        // If post is found, return it with HTTP 200 OK; otherwise, return 404 Not Found
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/posts: Creates a new post
    // @RequestBody maps the JSON request body to a Post object
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Sets HTTP status to 201 Created on successful creation
    public Post createPost(@RequestBody Post post) {
        // Set creation and update timestamps upon creation
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        // Ensure the post is not published by default unless explicitly set to true in
        // request
        // This allows for creating drafts
        if (post.isPublished()) {
            // If the request body explicitly sets published to true, keep it.
        } else {
            post.setPublished(false); // Default to false (draft) if not specified or false
        }
        return postRepository.save(post); // Save the new post to the database
    }

    // You can add more endpoints here, e.g., PUT for updating posts, DELETE for
    // deleting posts
    // For a personal profile, you might only need GET for public access and POST
    // for adding content (e.g., via Postman/admin)
}
