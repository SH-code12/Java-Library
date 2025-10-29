package InfrastructureLayer.mutation;

import java.util.ArrayList;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.FloatingChromosome;

public class Floating_NonUniform implements MutationStrategy<Double> {
    private final Random rand = new Random();

    // Helper wrapper to reuse FloatingChromosome structure
    private static class FloatingChromosomeFromGenes extends FloatingChromosome {
        public FloatingChromosomeFromGenes(ArrayList<Gene<Double>> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Non-Uniform Mutation ---
    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome, double mutationRate) {
        ArrayList<Gene<Double>> mutatedGenes = new ArrayList<>();
        double b = 5.0; // shape parameter controlling non-uniformity

        for (Gene<Double> gene : chromosome.getGenes()) {
            Gene<Double> newGene = gene.copy();

            if (rand.nextDouble() < mutationRate) {
                double u = rand.nextDouble();
                double delta = Math.pow(1 - u, b) * (rand.nextBoolean() ? 1 : -1);
                newGene.setValue(newGene.getValue() + delta);
            }

            mutatedGenes.add(newGene);
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }
}
