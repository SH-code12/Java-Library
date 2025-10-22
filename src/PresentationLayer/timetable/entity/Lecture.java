package PresentationLayer.timetable.entity;

import java.util.Arrays;
import java.util.List;

public class Lecture {
    String name;
    int id;
    String doctor;
    int lengthHours;
    int numStudents;
    List<String> registeredCourses; // Used for Student Clash Constraint

    public Lecture(int id,String name, String doc, int len, int students, String... courses) {
        this.id = id;
        this.name = name; this.doctor = doc; this.lengthHours = len;

        this.numStudents = students; this.registeredCourses = Arrays.asList(courses);
    }
}
