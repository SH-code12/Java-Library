package DomainLayer.entities;

import java.util.List;

public abstract class Chromosome<T>{
    protected List<T> genes; 

    public Chromosome(List<T> genes) {
        this.genes = genes;
    }

    public List<T> getGenes() {
        return genes;
    }

    public void setGenes(List<T> genes) {
        this.genes = genes;
    }

    public abstract void initializeGenes();
    
    public abstract int ChromosomeLength();
    public abstract double getFitness();

}
