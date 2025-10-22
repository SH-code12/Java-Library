package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;

public class IntegerChromosome extends Chromosome<Integer> {
    private int numberOfCourses;
    private int maxSlot;
    private int maxRoom;

    public IntegerChromosome(int numberOfCourses, int maxSlot, int maxRoom) {
        super(new ArrayList<>(), 0.0);
        this.numberOfCourses = numberOfCourses;
        this.maxSlot = maxSlot;
        this.maxRoom = maxRoom;
        initializeGenes();
    }

    public void initializeGenes() {
        Random rand = new Random();
        for (int i = 0; i < numberOfCourses; i++) {
            int slot = rand.nextInt(maxSlot) + 1;
            int room = rand.nextInt(maxRoom) + 1;
            genes.add(slot);
            genes.add(room);
        }
    }

    public int ChromosomeLength() {
        return 2 * numberOfCourses;
    }

    @Override
    public Chromosome<Integer> createNew(List<Integer> genes) {
        IntegerChromosome newChromosome = new IntegerChromosome(numberOfCourses, maxSlot, maxRoom);
        newChromosome.setGenes(genes);
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
         Random rand = new Random();
        this.setFitness(rand.nextDouble());
    }

  

}
