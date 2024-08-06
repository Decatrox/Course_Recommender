package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.assessment.Assessment;
import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.rating.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.List;
import java.util.UUID;

//@Component
//@Scope("prototype")
@Entity
@Getter @Setter @ToString
public class Course {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private int credit;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToMany
    @JoinTable(
            name = "course_author"
    )
    private List<Author> authors;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private Assessment assessment;

//    public Course() {
//        this.id = UUID.randomUUID();
//    }


}
