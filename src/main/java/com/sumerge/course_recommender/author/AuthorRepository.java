package com.sumerge.course_recommender.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author findByEmail(String email);
//    Boolean existsByEmail(String email);
}
