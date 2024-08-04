package com.sumerge.course_recommender;

import com.sumerge.course_recommender.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CourseRecommenderApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CourseService courseService = context.getBean(CourseService.class);
        System.out.println(courseService.getRecommendedCourses().get(0).toString());
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
