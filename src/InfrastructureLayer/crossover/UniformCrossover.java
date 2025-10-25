package InfrastructureLayer.crossover;

import java.util.*;
import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.CrossoverStrategy;

public class UniformCrossover<G, T extends Chromosome<G>> implements CrossoverStrategy<G, T> {

    private final List<T> matingPool;
    private final List<T> nextGeneration = new ArrayList<>();
    private final Random rand = new Random();

    public UniformCrossover(List<T> matingPool) {
        this.matingPool = matingPool;
        generateNextGeneration();
    }

    private void generateNextGeneration() {
        int n = matingPool.size();
        while (nextGeneration.size() < n) {
            List<T> parents = selectParents();
            List<T> children = crossOver(parents);
            for (T child : children) {
                if (nextGeneration.size() < n) nextGeneration.add(child);
            }
        }
        nextGeneration.forEach(T::calculateFitnessValue);
    }

    @Override
    public List<T> crossOver(List<T> parents) {
        T p1 = parents.get(0);
        T p2 = parents.get(1);
        int length = p1.getGenes().size();
        List<G> child1Genes = new ArrayList<>();
        List<G> child2Genes = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            if (rand.nextBoolean()) {
                child1Genes.add(p1.getGenes().get(i));
                child2Genes.add(p2.getGenes().get(i));
            } else {
                child1Genes.add(p2.getGenes().get(i));
                child2Genes.add(p1.getGenes().get(i));
            }
        }

        T child1 = (T) p1.createNew(child1Genes);
        T child2 = (T) p2.createNew(child2Genes);
        return Arrays.asList(child1, child2);
    }

    private List<T> selectParents() {
        int n = matingPool.size();
        T p1 = matingPool.get(rand.nextInt(n));
        T p2;
        do {
            p2 = matingPool.get(rand.nextInt(n));
        } while (p1 == p2);
        return Arrays.asList(p1, p2);
    }

    @Override
    public List<T> getNextGeneration() {
        return nextGeneration;
    }
}
