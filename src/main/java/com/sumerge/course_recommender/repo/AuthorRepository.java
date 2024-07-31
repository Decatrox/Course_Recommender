package com.sumerge.course_recommender.repo;

import com.sumerge.course_recommender.model.Author;
import com.sumerge.course_recommender.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class AuthorRepository {

    private JdbcTemplate jdbcTemplate;

    public void addAuthor(Author author) {
        String sql = "insert into authors values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, author.getId(), author.getName(), author.getEmail(), author.getBirthdate());
    }

    public Author getAuthor(UUID author_id) {
        String sql = "select * from authors where id = ?";
        return (Author)jdbcTemplate.query(sql, new BeanPropertyRowMapper<Author>(Author.class), author_id).toArray()[0];
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
