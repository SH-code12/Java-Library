package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import DomainLayer.entities.Chromosome;

public class FloatingChromosome extends Chromosome<Double> {

    private int numberOfCourses;
    private int maxSlot;
    private int maxRoom;

    public FloatingChromosome(int numberOfCourses, int maxSlot, int maxRoom) {
        super(new ArrayList<>(), 0.0);
        this.numberOfCourses = numberOfCourses;
        this.maxSlot = maxSlot;
        this.maxRoom = maxRoom;
        initializeGenes();
    }

    @Override
    public void initializeGenes() {
        Random rand = new Random();
        for (int i = 0; i < numberOfCourses; i++) {
            double slot = 1 + rand.nextDouble() * (maxSlot - 1);
            double room = 1 + rand.nextDouble() * (maxRoom - 1);
            genes.add(slot);
            genes.add(room);
        }
    }

    @Override
    public int ChromosomeLength() {
        return 2 * numberOfCourses;
    }

    @Override
    public Chromosome<Double> createNew(List<Double> genes) {
        FloatingChromosome newChromosome = new FloatingChromosome(numberOfCourses, maxSlot, maxRoom);
        newChromosome.setGenes(new ArrayList<>(genes));
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        // Replace with domain-specific fitness in Timetable case
        this.setFitness(new Random().nextDouble());
    }
}
