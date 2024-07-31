package com.sumerge.course_recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CourseRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRecommenderApplication.class, args);
    }

}

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//public class CourseRecommenderApplication {
//
//    public static void main(String[] args) {
//
//        ApplicationContext context = SpringApplication.run(CourseRecommenderApplication.class, args);
//        CourseService service = context.getBean(CourseService.class);
//        service.getRecommendedCourses();
//    }
//
//}
