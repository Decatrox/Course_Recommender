package com.sumerge.course_recommender.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssessmentRepository {

    private JdbcTemplate template;

    public void save(Assessment assessment) {
        System.out.println("Saving");

        String sql = "insert into assessment values(?,?)";
        int rows = template.update(sql, assessment.getId(), assessment.getContent());
        System.out.println(rows + " rows affected");
    }

    public List<Assessment> findAll() {
        System.out.println("Retrieving all assessments");
        String sql = "select * from assessment";

        return new ArrayList<Assessment>(template.query(sql, new BeanPropertyRowMapper<Assessment>(Assessment.class)));
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

//    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
