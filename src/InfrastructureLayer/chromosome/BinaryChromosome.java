package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;

public class BinaryChromosome extends Chromosome<Integer> {

    private int geneCount;

    public BinaryChromosome(int geneCount) {
        super(new ArrayList<>(), 0.0);
        this.geneCount = geneCount;
        initializeGenes();
    }

    @Override
    public void initializeGenes() {
        Random rand = new Random();
        for (int i = 0; i < geneCount; i++) {
            int bit = rand.nextBoolean() ? 1 : 0;
            genes.add(new Gene<>(bit));
        }
    }

    @Override
    public int ChromosomeLength() {
        return geneCount;
    }

    @Override
    public Chromosome<Integer> createNew(List<Gene<Integer>> genes) {
        BinaryChromosome newChromosome = new BinaryChromosome(geneCount);
        newChromosome.setGenes(new ArrayList<>(genes));
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        // Example placeholder fitness: count of 1s
        double fitnessValue = genes.stream()
                .mapToInt(g -> g.getValue())
                .sum();

        this.setFitness(fitnessValue);
    }
}
