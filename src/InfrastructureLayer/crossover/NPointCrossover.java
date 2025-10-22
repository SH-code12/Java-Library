package InfrastructureLayer.crossover;

import java.util.*;
import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.CrossoverStrategy;

public class NPointCrossover<G, T extends Chromosome<G>> implements CrossoverStrategy {

    private List<T> matingPool;
    private List<T> nextGeneration = new ArrayList<>();
    private Random rand = new Random();

    public NPointCrossover(List<T> matingPool) {
        this.matingPool = matingPool;
        this.crossOver();
    }

    @Override
    public void crossOver() {
        int n = matingPool.size();

        while (nextGeneration.size() < n) {
            List<T> parents = selectParents(matingPool);
            T p1 = parents.get(0);
            T p2 = parents.get(1);

            List<Integer> crossOverPoints = getCrossOverPoints(p1.getGenes().size());

            T offSpring1 = getOffspring(p1, p2, crossOverPoints);
            T offSpring2 = getOffspring(p2, p1, crossOverPoints);

            if (nextGeneration.size() == n - 1) {
                double f1 = offSpring1.getFitness();
                double f2 = offSpring2.getFitness();
                if (f1 >= f2) {
                    nextGeneration.add(offSpring1);
                } else {
                    nextGeneration.add(offSpring2);
                }
            } else {
                nextGeneration.add(offSpring1);
                nextGeneration.add(offSpring2);
            }
        }
        for (T c : nextGeneration)
            c.calculateFitnessValue();
    }

    private List<T> selectParents(List<T> matingPool) {
        List<T> parents = new ArrayList<>();
        int n = matingPool.size();
        int idx1 = rand.nextInt(n);
        int idx2 = rand.nextInt(n);
        while (idx2 == idx1)
            idx2 = rand.nextInt(n);
        parents.add(matingPool.get(idx1));
        parents.add(matingPool.get(idx2));
        return parents;
    }

    private List<Integer> getCrossOverPoints(int length) {
        Set<Integer> points = new LinkedHashSet<>();
        int N = 1 + rand.nextInt(Math.max(1, length / 2));
        while (points.size() < N) {
            int number = 1 + rand.nextInt(length - 2);
            points.add(number);
        }

        List<Integer> sorted = new ArrayList<>(points);
        Collections.sort(sorted);
        return sorted;
    }

    private T getOffspring(T parent1, T parent2, List<Integer> points) {
        List<G> offSpringGenes = new ArrayList<>();
        int prev = 0;

        for (int i = 0; i < points.size(); ++i) {
            int next = points.get(i);
            for (int j = prev; j <= next; ++j) {
                if (i % 2 == 0)
                    offSpringGenes.add(parent1.getGenes().get(j));
                else
                    offSpringGenes.add(parent2.getGenes().get(j));
            }
            prev = next + 1;
        }

        for (int j = prev; j < parent1.getGenes().size(); ++j) {
            if (points.size() % 2 == 0)
                offSpringGenes.add(parent1.getGenes().get(j));
            else
                offSpringGenes.add(parent2.getGenes().get(j));
        }

        return (T) parent1.createNew(offSpringGenes);
    }

    @Override
    public List<T> getNextGeneration() {
        return this.nextGeneration;
    }
}
