package com.venkateshng.my_profile_site_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Data; // From Lombok dependency
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity // Marks this class as a JPA entity, mapped to a database table
@Data // Lombok annotation: automatically generates getters, setters, equals,
      // hashCode, toString
@NoArgsConstructor // Lombok: generates a no-argument constructor
@AllArgsConstructor // Lombok: generates a constructor with all fields
public class Post {
    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID for PostgreSQL
    private Long id;

    private String title;
    @Column(unique = true) // Ensures slug is unique in the database
    private String slug; // URL-friendly version of the title
    @Column(columnDefinition = "TEXT") // Allows for longer text content in the database
    private String content; // Full content of the blog post (can be Markdown or HTML)
    private String excerpt; // Short summary for listing
    private String imageUrl; // Optional URL for a featured image
    private boolean published; // Whether the post is visible publicly

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection // For collections of basic types (like tags)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag") // Column name for the tags in the join table
    private List<String> tags; // Array of strings, e.g., ["react", "frontend"]

    // You'll add a Project entity similarly later for your portfolio
}
