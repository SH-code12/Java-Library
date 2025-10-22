package InfrastructureLayer.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;

public class TournametSelection<T extends Chromosome<?>> {

    private List<T> searchSpace;
    int tournametSize;
    private List<T> matingPool = new ArrayList<>();

    public TournametSelection(List<T> searchSpace, int tournametSize) {
        this.searchSpace = searchSpace;
        this.tournametSize = tournametSize;
        int n = searchSpace.size();
        if (tournametSize <= 0 || tournametSize > n) {
            throw new IllegalArgumentException("Tournament size must be between 1 and population size");
        }
        this.select(tournametSize);
    }

    private void select(int tournametSize) {
        Random rand = new Random();
        int n = searchSpace.size();
        for (int i = 0; i < n; ++i) {
            T best = null;
            for (int j = 0; j < tournametSize; ++j) {
                int idx = rand.nextInt(n);
                T candidate = searchSpace.get(idx);
                if (best == null || candidate.getFitness() > best.getFitness())
                    best = candidate;
            }
            matingPool.add(best);
        }

    }

    public List<T> getMatingPool() {
        return matingPool;
    }
}
