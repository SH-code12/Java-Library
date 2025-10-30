package InfrastructureLayer.crossover;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.CrossoverStrategy;

import java.util.*;

/**
 * N-Point Crossover implementation using Gene<T>.
 */
public class NPointCrossover<G, T extends Chromosome<G>> implements CrossoverStrategy<G, T> {

    private final List<T> matingPool;
    private final List<T> nextGeneration = new ArrayList<>();
    private final Random rand = new Random();

    public NPointCrossover(List<T> matingPool) {
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
        List<Integer> points = getCrossOverPoints(length);

        List<Gene<G>> child1Genes = new ArrayList<>();
        List<Gene<G>> child2Genes = new ArrayList<>();

        boolean swap = false;
        int last = 0;

        for (int point : points) {
            for (int i = last; i < point; i++) {
                if (swap) {
                    child1Genes.add(p2.getGenes().get(i));
                    child2Genes.add(p1.getGenes().get(i));
                } else {
                    child1Genes.add(p1.getGenes().get(i));
                    child2Genes.add(p2.getGenes().get(i));
                }
            }
            swap = !swap;
            last = point;
        }

        for (int i = last; i < length; i++) {
            if (swap) {
                child1Genes.add(p2.getGenes().get(i));
                child2Genes.add(p1.getGenes().get(i));
            } else {
                child1Genes.add(p1.getGenes().get(i));
                child2Genes.add(p2.getGenes().get(i));
            }
        }

        @SuppressWarnings("unchecked")
        T child1 = (T) p1.createNew(child1Genes);
        @SuppressWarnings("unchecked")
        T child2 = (T) p2.createNew(child2Genes);
        return Arrays.asList(child1, child2);
    }

    private List<Integer> getCrossOverPoints(int length) {
        int numPoints = 1 + rand.nextInt(Math.max(1, length / 2));
        Set<Integer> points = new TreeSet<>();
        while (points.size() < numPoints) {
            points.add(1 + rand.nextInt(Math.max(1, length - 2)));
        }
        return new ArrayList<>(points);
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

    public List<T> getNextGeneration() {
        return nextGeneration;
    }
}
