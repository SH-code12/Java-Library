package PresentationLayer.timetable;

import PresentationLayer.timetable.entity.Lecture;
import PresentationLayer.timetable.entity.Room;

import java.util.Arrays;
import java.util.List;

public class TimetableChromosome
{
  private final int[][] schedule;
   private final TimeTablePhenoType useCase;
   public TimetableChromosome(TimeTablePhenoType useCase){
     this.useCase = useCase;
     this.schedule = new int[useCase.numOfRooms][useCase.numOfSlots];
     for(int i =0;i<useCase.numOfRooms;i++){
       Arrays.fill(this.schedule[i],-1);
     }
   }
  public TimetableChromosome(TimeTablePhenoType useCase, int[][] schedule) {
    this.useCase = useCase;
    this.schedule = schedule;
  }
  public TimeTablePhenoType getContext() { return useCase; }

  public int[][] getSchedule() { return schedule; }

  public int getLectureId(int roomIndex, int slotIndex) {
    return schedule[roomIndex][slotIndex];
  }

  public void setLectureId(int roomIndex, int slotIndex, int lectureId) {
     if(roomIndex>=0&&roomIndex<this.useCase.numOfRooms&&slotIndex>=0&&slotIndex<this.useCase.numOfSlots)
    schedule[roomIndex][slotIndex] = lectureId;

  }
  public TimetableChromosome copy() {
    int[][] newSchedule = new int[useCase.numOfRooms][useCase.numOfSlots];
    for (int r = 0; r < useCase.numOfRooms; r++) {
      System.arraycopy(this.schedule[r], 0, newSchedule[r], 0,useCase.numOfSlots);
    }
    return new TimetableChromosome(useCase, newSchedule);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < useCase.numOfRooms; r++) {
      sb.append(useCase.getRoom(r).name).append(": [");
      for (int s = 0; s < useCase.numOfSlots; s++) {
        sb.append(schedule[r][s] == -1 ? "---" : String.format("%03d", schedule[r][s]));
        if (s < useCase.numOfSlots - 1) sb.append(", ");
      }
      sb.append("]\n");
    }
    return sb.toString();
  }

  }
