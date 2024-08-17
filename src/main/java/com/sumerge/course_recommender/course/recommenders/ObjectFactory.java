
package com.sumerge.course_recommender.course.recommenders;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sumerge.course_recommender.course.recommenders package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Course_QNAME = new QName("com.sumerge.course", "Course");
    private static final QName _GetCoursesRequest_QNAME = new QName("com.sumerge.course-wsdl", "GetCoursesRequest");
    private static final QName _FirstName_QNAME = new QName("com.sumerge.course-wsdl", "firstName");
    private static final QName _LastName_QNAME = new QName("com.sumerge.course-wsdl", "lastName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sumerge.course_recommender.course.recommenders
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCoursesResponse }
     * 
     * @return
     *     the new instance of {@link GetCoursesResponse }
     */
    public GetCoursesResponse createGetCoursesResponse() {
        return new GetCoursesResponse();
    }

    /**
     * Create an instance of {@link GetCoursesResponse.Course }
     * 
     * @return
     *     the new instance of {@link GetCoursesResponse.Course }
     */
    public GetCoursesResponse.Course createGetCoursesResponseCourse() {
        return new GetCoursesResponse.Course();
    }

    /**
     * Create an instance of {@link com.sumerge.course_recommender.course.recommenders.Course }
     * 
     * @return
     *     the new instance of {@link com.sumerge.course_recommender.course.recommenders.Course }
     */
    public com.sumerge.course_recommender.course.recommenders.Course createCourse() {
        return new com.sumerge.course_recommender.course.recommenders.Course();
    }

    /**
     * Create an instance of {@link com.sumerge.course_recommender.course.recommenders.Professor }
     * 
     * @return
     *     the new instance of {@link com.sumerge.course_recommender.course.recommenders.Professor }
     */
    public com.sumerge.course_recommender.course.recommenders.Professor createProfessor() {
        return new com.sumerge.course_recommender.course.recommenders.Professor();
    }

    /**
     * Create an instance of {@link BaseCourse }
     * 
     * @return
     *     the new instance of {@link BaseCourse }
     */
    public BaseCourse createBaseCourse() {
        return new BaseCourse();
    }

    /**
     * Create an instance of {@link GetCoursesResponse.Course.Professor }
     * 
     * @return
     *     the new instance of {@link GetCoursesResponse.Course.Professor }
     */
    public GetCoursesResponse.Course.Professor createGetCoursesResponseCourseProfessor() {
        return new GetCoursesResponse.Course.Professor();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link com.sumerge.course_recommender.course.recommenders.Course }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link com.sumerge.course_recommender.course.recommenders.Course }{@code >}
     */
    @XmlElementDecl(namespace = "com.sumerge.course", name = "Course")
    public JAXBElement<com.sumerge.course_recommender.course.recommenders.Course> createCourse(com.sumerge.course_recommender.course.recommenders.Course value) {
        return new JAXBElement<>(_Course_QNAME, com.sumerge.course_recommender.course.recommenders.Course.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "com.sumerge.course-wsdl", name = "GetCoursesRequest")
    public JAXBElement<String> createGetCoursesRequest(String value) {
        return new JAXBElement<>(_GetCoursesRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "com.sumerge.course-wsdl", name = "firstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "com.sumerge.course-wsdl", name = "lastName")
    public JAXBElement<String> createLastName(String value) {
        return new JAXBElement<>(_LastName_QNAME, String.class, null, value);
    }

}
