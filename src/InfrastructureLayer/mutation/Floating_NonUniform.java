package InfrastructureLayer.mutation;
//// Two Methods
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.FloatingChromosome;

public class Floating_NonUniform implements MutationStrategy<Double> {
    private final Random rand = new Random();

    private static class FloatingChromosomeFromGenes extends FloatingChromosome {
        public FloatingChromosomeFromGenes(List<Double> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Non-Uniform Mutation ---
    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome, double mutationRate) {
        List<Double> genes = new ArrayList<>(chromosome.getGenes());
        List<Double> mutatedGenes = new ArrayList<>();
// shape parameter controlling how non-uniform the change is
        double b = 5.0;

        for (double value : genes) {
            if (rand.nextDouble() < mutationRate) {
                double u = rand.nextDouble();
                double delta = Math.pow(1 - u, b) * (rand.nextBoolean() ? 1 : -1);
                mutatedGenes.add(value + delta);
            } else {
                mutatedGenes.add(value);
            }
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }

}

