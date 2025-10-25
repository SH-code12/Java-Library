package DomainLayer.entities;

import java.util.List;

/**
 * Generic abstract representation of a chromosome in a Genetic Algorithm.
 * Supports any type of gene (Integer, Double, Boolean, String, etc.)
 */
public abstract class Chromosome<T> {
    protected List<T> genes;
    protected double fitness;

    public Chromosome(List<T> genes) {
        this.genes = genes;
        this.fitness = 0.0;
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

    /** Randomly initialize genes (domain-specific). */
    public abstract void initializeGenes();

    /** Returns the total number of genes in this chromosome. */
    public abstract int ChromosomeLength();

    /** Creates a new chromosome instance with the given genes. */
    public abstract Chromosome<T> createNew(List<T> genes);

    /** Computes the fitness value using a FitnessFunction strategy. */
    public abstract void calculateFitnessValue();
}

