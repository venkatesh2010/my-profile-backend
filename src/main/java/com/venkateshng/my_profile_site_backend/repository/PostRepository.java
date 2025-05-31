package com.venkateshng.my_profile_site_backend.repository;

import com.venkateshng.my_profile_site_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Marks this as a Spring Data JPA repository component
public interface PostRepository extends JpaRepository<Post, Long> {
    // JpaRepository provides standard CRUD methods (save, findById, findAll, delete, etc.)

    // Custom query method: find a post by its unique slug
    Optional<Post> findBySlug(String slug);

    // Custom query method: find all published posts, ordered by creation date (newest first)
    List<Post> findByPublishedTrueOrderByCreatedAtDesc();
}
