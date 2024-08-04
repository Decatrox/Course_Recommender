package com.sumerge.course_recommender.config;

import com.sumerge.course_recommender.CourseRecommender;
import com.sumerge.course_recommender.CourseRecommenderBad;
import com.sumerge.course_recommender.CourseRecommenderFun;
import com.sumerge.course_recommender.CourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
//@ComponentScan(basePackages = "com.sumerge.course_recommender")
public class AppConfig {

    @Bean(name = "funRecommender")
    @Primary
    public CourseRecommender funRecommender() {
        return new CourseRecommenderFun();
    }


    @Bean(name = "badRecommender")
//    @Qualifier("bad")
    public CourseRecommender badRecommender() {
        return new CourseRecommenderBad();
    }

    @Bean
    public CourseService courseService(@Qualifier("funRecommender") CourseRecommender courseRecommender) {
        return new CourseService(courseRecommender);
    }

}

