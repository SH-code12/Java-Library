package PresentationLayer.timetable.entity;

public class Room {
    public final String name;
    public final int capacity;
    public final int maxDailyHours;
    public final int maxConsecutiveHours;

    public Room(String name, int capacity, int maxDaily, int maxCons) {
         this.name = name; this.capacity = capacity;
        this.maxDailyHours = maxDaily; this.maxConsecutiveHours = maxCons;
    }
}
