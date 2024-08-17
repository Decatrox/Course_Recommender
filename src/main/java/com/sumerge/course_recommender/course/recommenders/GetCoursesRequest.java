//package com.sumerge.course_recommender.course.recommenders;
//
//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement(name = "GetCoursesRequest", namespace = "com.sumerge.course-wsdl")
//public class GetCoursesRequest {
//    // This class intentionally left empty since the request has no fields
//}
package com.sumerge.course_recommender.course.recommenders;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetCoursesRequest", namespace = "com.sumerge.course-wsdl")
public class GetCoursesRequest {

    private String name;

//    public GetCoursesRequest() {
//    }
//
//    public GetCoursesRequest(String name) {
//        this.name = name;
//    }
//
//    @XmlElement(required = true)
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}