package InfrastructureLayer.mutation;
//// Two Methods
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.FloatingChromosome;

public class FloatingMutation implements MutationStrategy<Double> {
    private final Random rand = new Random();

    private static class FloatingChromosomeFromGenes extends FloatingChromosome {
        public FloatingChromosomeFromGenes(List<Double> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Uniform Mutation ---
    @Override
    public Chromosome<Double> mutateFirstMethod(Chromosome<Double> chromosome, double mutationRate) {
        List<Double> genes = new ArrayList<>(chromosome.getGenes());
        List<Double> mutatedGenes = new ArrayList<>();

        for (double value : genes) {
            if (rand.nextDouble() < mutationRate) {
                double delta = (rand.nextDouble() * 2 - 1); // [-1, +1]
                mutatedGenes.add(value + delta);
            } else {
                mutatedGenes.add(value);
            }
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }

    // --- Non-Uniform Mutation ---
    @Override
    public Chromosome<Double> mutateSecondMethod(Chromosome<Double> chromosome, double mutationRate) {


}

