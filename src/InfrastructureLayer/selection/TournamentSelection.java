package InfrastructureLayer.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.SelectionStrategy;

public class TournamentSelection<T extends Chromosome<?>> implements SelectionStrategy<T> {

    private final int tournamentSize;
    private final Random rand = new Random();

    public TournamentSelection(int tournamentSize) {
        if (tournamentSize <= 0) {
            throw new IllegalArgumentException("Tournament size must be greater than 0");
        }
        this.tournamentSize = tournamentSize;
    }

    @Override
    public List<T> select(List<T> population) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty");
        }

        if (tournamentSize > population.size()) {
            throw new IllegalArgumentException("Tournament size cannot exceed population size");
        }

        List<T> matingPool = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            T best = null;
            for (int j = 0; j < tournamentSize; j++) {
                int idx = rand.nextInt(population.size());
                T candidate = population.get(idx);
                if (best == null || candidate.getFitness() > best.getFitness()) {
                    best = candidate;
                }
            }
            matingPool.add(best);
        }

        return matingPool;
    }
}
