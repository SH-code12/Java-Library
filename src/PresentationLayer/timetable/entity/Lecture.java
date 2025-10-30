package PresentationLayer.timetable.entity;

import java.util.Arrays;
import java.util.List;

public class Lecture {
    private final String name;
    private final int id;
    private final String doctor;
    private final int lengthHours;
    private final int numStudents;
    private final List<String> registeredCourses;

    public Lecture(int id, String name, String doc, int len, int students, String... courses) {
        this.id = id;
        this.name = name;
        this.doctor = doc;
        this.lengthHours = len;
        this.numStudents = students;
        this.registeredCourses = Arrays.asList(courses);
    }

    // Getters
    public String getName() { return name; }
    public int getId() { return id; }
    public String getDoctor() { return doctor; }
    public int getLengthHours() { return lengthHours; }
    public int getNumStudents() { return numStudents; }
    public List<String> getRegisteredCourses() { return registeredCourses; }
}
