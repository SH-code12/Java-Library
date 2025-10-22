package InfrastructureLayer.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;

public class RouletteWheelSelection<T extends Chromosome<?>> {

    private List<T> searchSpace;
    private List<T> matingPool = new ArrayList<>();

    public RouletteWheelSelection(List<T> searchSpace) {
        this.searchSpace = searchSpace;
        this.select();
    }

    private List<Double> calculateCumulativeRange(List<T> ss) {
        List<Double> range = new ArrayList<>();
        double totalFitness = 0.0;
        for (T chr : ss)
            totalFitness += chr.getFitness();

        double cumulative = 0.0;
        for (T chr : ss) {
            cumulative += chr.getFitness() / totalFitness;
            range.add(cumulative);
        }

        // Ensure the last value is exactly 1.0
        range.set(range.size() - 1, 1.0);
        return range;
    }

    private List<Double> generateRandoms(int n) {
        Random r = new Random();
        List<Double> randoms = new ArrayList<>();
        for (int i = 0; i < n; i++)
            randoms.add(r.nextDouble());
        return randoms;
    }

    private void select() {
        int n = searchSpace.size();
        List<Double> cumulativeRange = calculateCumulativeRange(searchSpace);
        List<Double> randoms = generateRandoms(n);

        for (double rand : randoms) {
            for (int j = 0; j < cumulativeRange.size(); j++) {
                if (rand <= cumulativeRange.get(j)) {
                    matingPool.add(searchSpace.get(j));
                    break;
                }
            }
        }
    }

    public List<T> getMatingPool() {
        return matingPool;
    }
}
