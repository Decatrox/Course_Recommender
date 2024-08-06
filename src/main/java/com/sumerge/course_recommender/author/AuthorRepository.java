package com.sumerge.course_recommender.author;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author findByEmail(String email);
}
