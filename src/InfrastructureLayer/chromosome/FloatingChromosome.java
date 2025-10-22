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

    public void initializeGenes() {
        Random rand = new Random();
        for (int i = 0; i < numberOfCourses; i++) {
            double slot = 1 + rand.nextDouble() * (maxSlot - 1); // بين 1 و maxSlot
            double room = 1 + rand.nextDouble() * (maxRoom - 1); // بين 1 و maxRoom
            genes.add(slot);
            genes.add(room);
        }
    }

    public int ChromosomeLength() {
        return 2 * numberOfCourses;
    }

    @Override
    public Chromosome<Double> createNew(List<Double> genes) {
        FloatingChromosome newChromosome = new FloatingChromosome(numberOfCourses, maxSlot, maxRoom);
        newChromosome.setGenes(genes);
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        Random rand = new Random();
        this.setFitness(rand.nextDouble());
    }

}
