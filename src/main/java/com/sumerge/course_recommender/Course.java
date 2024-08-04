package com.sumerge.course_recommender;

public class Course {
    private String course_name;
    private String course_description;
    private int course_credits;
    private String course_professor;

    public Course(String course_name, String course_description, int course_credits, String course_professor) {
        this.course_name = course_name;
        this.course_description = course_description;
        this.course_credits = course_credits;
        this.course_professor = course_professor;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public int getCourse_credits() {
        return course_credits;
    }

    public void setCourse_credits(int course_credits) {
        this.course_credits = course_credits;
    }

    public String getCourse_professor() {
        return course_professor;
    }

    public void setCourse_professor(String course_professor) {
        this.course_professor = course_professor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_name='" + course_name + '\'' +
                ", course_description='" + course_description + '\'' +
                ", course_credits=" + course_credits +
                ", course_professor='" + course_professor + '\'' +
                '}';
    }
}
