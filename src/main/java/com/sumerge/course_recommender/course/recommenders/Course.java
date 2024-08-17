
package com.sumerge.course_recommender.course.recommenders;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Course complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Course">
 *   <complexContent>
 *     <extension base="{com.sumerge.basecourse}BaseCourse">
 *       <sequence>
 *         <element name="professor" type="{com.sumerge.professor}Professor"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Course", namespace = "com.sumerge.course", propOrder = {
    "professor"
})
public class Course
    extends BaseCourse
{

    @XmlElement(required = true)
    protected Professor professor;

    /**
     * Gets the value of the professor property.
     * 
     * @return
     *     possible object is
     *     {@link Professor }
     *     
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * Sets the value of the professor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Professor }
     *     
     */
    public void setProfessor(Professor value) {
        this.professor = value;
    }

}
