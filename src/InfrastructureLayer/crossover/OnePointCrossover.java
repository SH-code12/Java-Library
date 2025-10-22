package InfrastructureLayer.crossover;

import java.util.*;
import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.CrossoverStrategy;

public class OnePointCrossover<G, T extends Chromosome<G>> implements CrossoverStrategy {

    private List<T> matingPool;
    private List<T> nextGeneration = new ArrayList<>();
    private Random rand = new Random();

    public OnePointCrossover(List<T> matingPool) {
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

            int crossOverPoint = getCrossOverPoint(p1.getGenes().size());

            T offSpring1 = getOffspring(p1, p2, crossOverPoint, p1.ChromosomeLength());
            T offSpring2 = getOffspring(p2, p1, crossOverPoint, p2.ChromosomeLength());

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

    private int getCrossOverPoint(int length) {

        int number = 1 + rand.nextInt(length - 2);
        return number;
    }

    private T getOffspring(T parent1, T parent2, int point, int n) {
        List<G> offSpringGenes = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (i < point) {
                offSpringGenes.add(parent1.getGenes().get(i));
            } else {
                offSpringGenes.add(parent2.getGenes().get(i));
            }
        }

        return (T) parent1.createNew(offSpringGenes);
    }

    @Override
    public List<T> getNextGeneration() {
        return this.nextGeneration;
    }
}
