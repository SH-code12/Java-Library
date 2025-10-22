package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;

public class BinaryChromosome extends Chromosome<Integer> {

    private int numberOfCourses;
    private int maxSlot;
    private int maxRoom;
    private int bitsPerSlot;
    private int bitsPerRoom;

    public BinaryChromosome(int numberOfCourses, int maxSlot, int maxRoom) {
        super(new ArrayList<>(), 0.0);
        this.numberOfCourses = numberOfCourses;
        this.maxSlot = maxSlot;
        this.maxRoom = maxRoom;

        this.bitsPerSlot = (int) Math.ceil(Math.log(maxSlot) / Math.log(2));
        this.bitsPerRoom = (int) Math.ceil(Math.log(maxRoom) / Math.log(2));

        initializeGenes();
    }

    public void initializeGenes() {
        Random rand = new Random();
        int totalBits = numberOfCourses * (bitsPerSlot + bitsPerRoom);
        for (int i = 0; i < totalBits; i++) {
            genes.add(rand.nextInt(2));
        }
    }

    public int ChromosomeLength() {
        return numberOfCourses * (bitsPerSlot + bitsPerRoom);
    }

    @Override
    public Chromosome<Integer> createNew(List<Integer> genes) {
        BinaryChromosome newChromosome = new BinaryChromosome(numberOfCourses, maxSlot, maxRoom);
        newChromosome.setGenes(genes);
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        Random rand = new Random();
        this.setFitness(rand.nextDouble());
    }

}
