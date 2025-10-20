package PresentationLayer.timetable;

import PresentationLayer.timetable.entity.Lecture;
import PresentationLayer.timetable.entity.Room;

import java.util.List;

public class TimeTablePhenoType {
    // I am making a schedule for only one day
    /* the phenotype representation of the solution is 2D schedule where
        rooms on x part
        time slots  y part

     */
    private final List<Lecture> lectures;
    private final List<Room> rooms;
    private final List<Integer> slots;
    public final int numOfRooms;
    public final int numOfSlots;




    public TimeTablePhenoType(List<Lecture> lectures, List<Room> rooms, List<Integer> slots) {
        this.lectures = lectures;
        this.rooms = rooms;
        this.slots = slots;
        numOfSlots = slots.size();
        numOfRooms = rooms.size();
    }

    public TimeTablePhenoType(List<Lecture> lectures, List<Room> rooms, List<Integer> slots, int numOfRooms, int numOfSlots) {
        this.lectures = lectures;
        this.rooms = rooms;
        this.slots = slots;
        this.numOfRooms = numOfRooms;
        this.numOfSlots = numOfSlots;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Integer> getSlots() {
        return slots;
    }
    public int getSlotIndexFromTime(int startTime) {
        return slots.indexOf(startTime);
    }

    public int getTimeFromSlotIndex(int index) {
        if (index < 0 || index >= numOfSlots) return -1;
        return slots.get(index);
    }

    public Room getRoom(int roomIndex) {
        if (roomIndex < 0 || roomIndex >= numOfRooms) return null;
        return rooms.get(roomIndex);
    }

    public Lecture getLecture(int lectureId) {
        if (lectureId < 0 || lectureId >= lectures.size()) return null;
        return lectures.get(lectureId);
    }
}
