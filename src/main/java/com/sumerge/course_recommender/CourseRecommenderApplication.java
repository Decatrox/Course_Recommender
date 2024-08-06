package com.sumerge.course_recommender;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class CourseRecommenderApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CourseRecommenderApplication.class, args);
    }
}
