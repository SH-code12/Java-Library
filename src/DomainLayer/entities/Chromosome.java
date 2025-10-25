package DomainLayer.entities;

import java.util.List;

public abstract class Chromosome<T> {
    protected List<T> genes;
    protected double fitness;

    public Chromosome(List<T> genes, double fitness) {
        this.genes = genes;
        this.fitness = fitness;
    }

    public List<T> getGenes() {
        return genes;
    }

    public void setGenes(List<T> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    // Abstract methods for flexibility
    public abstract void initializeGenes();
    public abstract int ChromosomeLength();
    public abstract Chromosome<T> createNew(List<T> genes);
    public abstract void calculateFitnessValue();

    // Clone method for safe copying
    public Chromosome<T> cloneChromosome() {
        return createNew(List.copyOf(genes));
    }
}
