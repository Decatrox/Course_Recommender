package com.sumerge.course_recommender.repo;

import com.sumerge.course_recommender.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CourseRepository {

    private JdbcTemplate jdbcTemplate;

    public void addCourse(Course course) {
        String sql = "insert into course values(?,?,?,?)";
        jdbcTemplate.update(sql, course.getId(), course.getName(), course.getDescription(), course.getCredit());
    }

    public void updateCourse(UUID course_id, Course course) {
        String sql = "update course set name = ?, description = ?, credit = ? where id = ?";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(), course_id);
    }

    public Course viewCourse(UUID course_id) {
        String sql = "select * from course where id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Course>(Course.class), course_id).getFirst();
    }

    public void deleteCourse(UUID course_id) {
        String sql = "delete from course where id = ?";
        jdbcTemplate.update(sql, course_id);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
