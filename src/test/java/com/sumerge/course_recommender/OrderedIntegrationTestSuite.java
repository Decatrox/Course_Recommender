package com.sumerge.course_recommender;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserControllerIntegrationTest.class,
        AuthorControllerIntegrationTest.class,
        CourseControllerIntegrationTest.class
})
public class OrderedIntegrationTestSuite {
}

