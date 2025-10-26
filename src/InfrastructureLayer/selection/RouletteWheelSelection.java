package InfrastructureLayer.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.SelectionStrategy;
import PresentationLayer.timetable.TimetableChromosome;

public class RouletteWheelSelection<T extends Chromosome<?>> implements SelectionStrategy<T> {

    private final Random rand = new Random();

    public RouletteWheelSelection(List<TimetableChromosome> individuals) {
    }

    @Override
    public List<T> select(List<T> population) {
        List<T> matingPool = new ArrayList<>();
        double totalFitness = population.stream().mapToDouble(T::getFitness).sum();

        if (totalFitness == 0) {
            // Avoid division by zero
            return new ArrayList<>(population);
        }

        // Compute cumulative probabilities
        List<Double> cumulative = new ArrayList<>();
        double sum = 0.0;
        for (T chr : population) {
            sum += chr.getFitness() / totalFitness;
            cumulative.add(sum);
        }

        // Ensure the last value is exactly 1.0
        cumulative.set(cumulative.size() - 1, 1.0);

        // Perform selection
        for (int i = 0; i < population.size(); i++) {
            double r = rand.nextDouble();
            for (int j = 0; j < cumulative.size(); j++) {
                if (r <= cumulative.get(j)) {
                    matingPool.add(population.get(j));
                    break;
                }
            }
        }
        return matingPool;
    }
}
