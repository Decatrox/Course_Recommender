
package com.sumerge.course_recommender.course.recommenders;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Course" maxOccurs="unbounded">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="credit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="professor">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             <element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "course"
})
@XmlRootElement(name = "GetCoursesResponse")
public class GetCoursesResponse {

    @XmlElement(name = "Course", required = true)
    protected List<GetCoursesResponse.Course> course;

    /**
     * Gets the value of the course property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the course property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getCourse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetCoursesResponse.Course }
     * </p>
     * 
     * 
     * @return
     *     The value of the course property.
     */
    public List<GetCoursesResponse.Course> getCourse() {
        if (course == null) {
            course = new ArrayList<>();
        }
        return this.course;
    }


    /**
     * <p>Java class for anonymous complex type</p>.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.</p>
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="credit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="professor">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   <element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 </sequence>
     *               </restriction>
     *             </complexContent>
     *           </complexType>
     *         </element>
     *       </sequence>
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "name",
        "description",
        "credit",
        "professor"
    })
    public static class Course {

        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String description;
        @XmlElement(required = true)
        protected String credit;
        @XmlElement(required = true)
        protected GetCoursesResponse.Course.Professor professor;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the credit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCredit() {
            return credit;
        }

        /**
         * Sets the value of the credit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCredit(String value) {
            this.credit = value;
        }

        /**
         * Gets the value of the professor property.
         * 
         * @return
         *     possible object is
         *     {@link GetCoursesResponse.Course.Professor }
         *     
         */
        public GetCoursesResponse.Course.Professor getProfessor() {
            return professor;
        }

        /**
         * Sets the value of the professor property.
         * 
         * @param value
         *     allowed object is
         *     {@link GetCoursesResponse.Course.Professor }
         *     
         */
        public void setProfessor(GetCoursesResponse.Course.Professor value) {
            this.professor = value;
        }


        /**
         * <p>Java class for anonymous complex type</p>.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.</p>
         * 
         * <pre>{@code
         * <complexType>
         *   <complexContent>
         *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       <sequence>
         *         <element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         <element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       </sequence>
         *     </restriction>
         *   </complexContent>
         * </complexType>
         * }</pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "firstName",
            "lastName"
        })
        public static class Professor {

            @XmlElement(required = true)
            protected String firstName;
            @XmlElement(required = true)
            protected String lastName;

            /**
             * Gets the value of the firstName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFirstName() {
                return firstName;
            }

            /**
             * Sets the value of the firstName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFirstName(String value) {
                this.firstName = value;
            }

            /**
             * Gets the value of the lastName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLastName() {
                return lastName;
            }

            /**
             * Sets the value of the lastName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLastName(String value) {
                this.lastName = value;
            }

        }

    }

}
