package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;

public class IntegerChromosome extends Chromosome<Integer> {

    private int geneCount;
    private int minValue;
    private int maxValue;

    public IntegerChromosome(int geneCount, int minValue, int maxValue) {
        super(new ArrayList<>(), 0.0);
        this.geneCount = geneCount;
        this.minValue = minValue;
        this.maxValue = maxValue;
        initializeGenes();
    }

    @Override
    public void initializeGenes() {
        Random rand = new Random();
        for (int i = 0; i < geneCount; i++) {
            int value = rand.nextInt(maxValue - minValue + 1) + minValue;
            genes.add(new Gene<>(value));
        }
    }

    @Override
    public int ChromosomeLength() {
        return geneCount;
    }

    @Override
    public String toString() {
        return "IntegerChromosome{" +
                "genes=" + getGenes() +
                '}';
    }

    @Override
    public Chromosome<Integer> createNew(List<Gene<Integer>> genes) {
        DomainLayer.entities.IntegerChromosome newChromosome = new DomainLayer.entities.IntegerChromosome(geneCount, minValue, maxValue);
        newChromosome.setGenes(new ArrayList<>(genes));
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        // Replace with your problem’s real fitness function
        this.setFitness(new Random().nextDouble());
    }
}
