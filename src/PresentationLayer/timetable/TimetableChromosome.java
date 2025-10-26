package PresentationLayer.timetable;

import DomainLayer.entities.Chromosome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimetableChromosome extends Chromosome<Integer> {

  private final TimeTablePhenoType context;

  public TimetableChromosome(TimeTablePhenoType context) {
    super(new ArrayList<>(), 0.0);
    this.context = context;
    initializeGenes();
  }

  public TimetableChromosome(TimeTablePhenoType context, List<Integer> genes) {
    super(genes, 0.0);
    this.context = context;
  }

  public TimeTablePhenoType getContext() {
    return context;
  }

  @Override
  public void initializeGenes() {
    genes.clear();
    int totalSlots = context.numOfRooms * context.numOfSlots;
    int totalLectures = context.getLectures().size();
    for (int i = 0; i < totalSlots; i++) {
      // Randomly assign lecture IDs or -1 (empty slot)
      genes.add(i < totalLectures ? i : -1);
    }
    java.util.Collections.shuffle(genes);
  }

  @Override
  public int ChromosomeLength() {
    return context.numOfRooms * context.numOfSlots;
  }

  @Override
  public Chromosome<Integer> createNew(List<Integer> genes) {
    return new TimetableChromosome(context, genes);
  }

  @Override
  public void calculateFitnessValue() {
    double fitness = 0.0;
    List<Integer> usedLectures = new ArrayList<>();
    int index = 0;

    for (int roomIdx = 0; roomIdx < context.numOfRooms; roomIdx++) {
      for (int slotIdx = 0; slotIdx < context.numOfSlots; slotIdx++) {
        int lectureId = genes.get(index++);
        if (lectureId == -1) continue;

        var lecture = context.getLecture(lectureId);
        var room = context.getRoom(roomIdx);

        // Basic fitness: room capacity + no duplicate lecture use
        if (!usedLectures.contains(lectureId)) {
          fitness += 10;
          usedLectures.add(lectureId);
        } else {
          fitness -= 5;
        }

        // Room capacity constraint
        if (lecture.getNumStudents() > room.capacity) {
          fitness -= 10;
        }

        // Room hours constraint
        if (lecture.getLengthHours() > room.maxConsecutiveHours) {
          fitness -= 5;
        }
      }
    }

    this.fitness = Math.max(0, fitness);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Timetable:\n");
    int index = 0;
    for (int r = 0; r < context.numOfRooms; r++) {
      sb.append(context.getRoom(r).name).append(": ");
      for (int s = 0; s < context.numOfSlots; s++) {
        int lectureId = genes.get(index++);
        sb.append(lectureId == -1 ? "---" : context.getLecture(lectureId).getName()).append(" | ");
      }
      sb.append("\n");
    }
    sb.append("Fitness: ").append(fitness);
    return sb.toString();
  }
}
