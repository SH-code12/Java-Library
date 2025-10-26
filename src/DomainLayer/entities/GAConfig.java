package DomainLayer.entities;

/**
 * Holds all configuration parameters for the Genetic Algorithm.
 * This makes the library reusable and tunable.
 */
public class GAConfig {
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int maxGenerations;

    public GAConfig(int populationSize, double crossoverRate, double mutationRate, int maxGenerations) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

}
