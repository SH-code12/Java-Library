package DomainLayer.entities;

import java.util.List;

public abstract class Chromosome<T> {
    protected List<Gene<T>> genes;
    protected double fitness;

    public Chromosome(List<Gene<T>> genes, double fitness) {
        this.genes = genes;
        this.fitness = fitness;
    }

    public List<Gene<T>> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene<T>> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    // Abstract methods
    public abstract void initializeGenes();
    public abstract int ChromosomeLength();
    public abstract Chromosome<T> createNew(List<Gene<T>> genes);
    public abstract void calculateFitnessValue();

    public Chromosome<T> cloneChromosome() {
        List<Gene<T>> copiedGenes = genes.stream()
                .map(Gene::copy)
                .toList();
        return createNew(copiedGenes);
    }
}
