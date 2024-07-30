package com.sumerge.course_recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CourseRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRecommenderApplication.class, args);
    }

}
