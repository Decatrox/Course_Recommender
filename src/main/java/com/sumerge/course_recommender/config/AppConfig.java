package com.sumerge.course_recommender.config;

import com.sumerge.CourseRecommenderBad;
import com.sumerge.course_recommender.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@ComponentScan(basePackages = "com.sumerge.course_recommender")
public class AppConfig {

    @Bean(name = "funRecommender")
    @Primary
    public CourseRecommender funRecommender() {
        return new CourseRecommenderFun();
    }


//    @Bean(name = "badRecommender")
////    @Qualifier("bad")
//    public CourseRecommender badRecommender() {
//        return new CourseRecommenderBad() {
//            @Override
//            public List<Course> recommendedCourses(){
//                List<Course> courses = new ArrayList<Course>();
//                courses.add(new Course("Circuits 2 (Overridden test)", "Seems fun but isn't", 4, "Someone"));
//                courses.add(new Course("Signals", "Why?", 4, "Another One"));
//                return courses;
//            }
//        };
//    }

    @Bean(name = "badRecommender")
//    @Qualifier("bad")
    public CourseRecommender badRecommender() {
        return new CourseRecommenderBad2();
    }

    @Bean
//    public CourseService courseService(CourseRecommender courseRecommender) {
    public CourseService courseService(@Qualifier("badRecommender") CourseRecommender courseRecommender) {
        return new CourseService(courseRecommender);
    }

}

