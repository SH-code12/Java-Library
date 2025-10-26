package ApplicationLayer.services;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.GAConfig;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generic implementation of the Genetic Algorithm cycle.
 * Works with any chromosome and gene type.
 */
public class GeneticAlgorithm<G, T extends Chromosome<G>> {

    private int populationSz;
    private int generationSz;
    private double mutationRate;
    private double crossoverRate;

    private FitnessFunction<T> fitnessFn;
    private SelectionStrategy<T> selectionStrategy;
    private CrossoverStrategy<G, T> crossoverStrategy;
    private MutationStrategy<G> mutationStrategy;
    private ReplacementStrategy<T> replacementStrategy;

    private Population<T> population;
    private T best;
    private double bestFitness = Double.NEGATIVE_INFINITY;
    private final Random rand = new Random();

    public GeneticAlgorithm() {
        this.population = new Population<>();
    }

    public GeneticAlgorithm(
            GAConfig config,
            FitnessFunction<T> fitnessFn,
            SelectionStrategy<T> selectionStrategy,
            CrossoverStrategy<G, T> crossoverStrategy,
            MutationStrategy<G> mutationStrategy,
            ReplacementStrategy<T> replacementStrategy,
            Population<T> population
    ) {
        this.populationSz = config.getPopulationSize();
        this.generationSz = config.getMaxGenerations();
        this.fitnessFn = fitnessFn;
        this.selectionStrategy = selectionStrategy;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.replacementStrategy = replacementStrategy;
        this.population = population;

    }

    /** Executes the Genetic Algorithm and returns the best chromosome found. */
    public T run() {
        validateConfiguration();

        for (int gen = 1; gen <= generationSz; gen++) {
            long start = System.currentTimeMillis();
            System.out.println("Generation " + gen);

            evaluatePopulation();

            List<T> children = new ArrayList<>();

            while (children.size() < populationSz) {
                List<T> parents = selectionStrategy.select(population.getChromosomes());
                List<T> offspring = crossoverStrategy.crossOver(parents);

                for (T child : offspring) {
                    mutationStrategy.mutate((Chromosome<G>) child, mutationRate);
                    children.add(child);
                }
            }

            population = replacementStrategy.replace(population, children);
            evaluatePopulation();

            long end = System.currentTimeMillis();
            System.out.println(" → Generation time: " + (end - start) + " ms");
        }

        System.out.println(" Best fitness found: " + bestFitness);
        return best;
    }


    /** Evaluate fitness and track the best chromosome. */
    private void evaluatePopulation() {
        for (T chromosome : population.getChromosomes()) {
            double fitness = fitnessFn.evaluate((Chromosome<T>) chromosome);
            chromosome.setFitness(fitness);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                best = chromosome;
            }
        }
    }

    /** Ensure all necessary components are configured. */
    private void validateConfiguration() {
        if (fitnessFn == null || selectionStrategy == null ||
                crossoverStrategy == null || mutationStrategy == null || replacementStrategy == null) {
            throw new IllegalStateException("Missing GA components. Ensure all strategies are set before running.");
        }
    }

    public double getHigherFitValue() {
        return bestFitness;
    }
}
