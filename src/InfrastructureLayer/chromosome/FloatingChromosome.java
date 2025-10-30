package InfrastructureLayer.chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;

public class FloatingChromosome extends Chromosome<Double> {

    private int geneCount;
    private double minValue;
    private double maxValue;

    public FloatingChromosome(int geneCount, double minValue, double maxValue) {
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
            double value = minValue + rand.nextDouble() * (maxValue - minValue);
            genes.add(new Gene<>(value));
        }
    }

    @Override
    public int ChromosomeLength() {
        return geneCount;
    }

    @Override
    public Chromosome<Double> createNew(List<Gene<Double>> genes) {
        FloatingChromosome newChromosome = new FloatingChromosome(geneCount, minValue, maxValue);
        newChromosome.setGenes(new ArrayList<>(genes));
        return newChromosome;
    }

    @Override
    public void calculateFitnessValue() {
        // Replace with your domain-specific fitness function
        this.setFitness(new Random().nextDouble());
    }
}


