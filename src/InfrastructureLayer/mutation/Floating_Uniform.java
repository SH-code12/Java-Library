package InfrastructureLayer.mutation;

import java.util.ArrayList;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import InfrastructureLayer.chromosome.FloatingChromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;

public class Floating_Uniform implements MutationStrategy<Double> {

    private final Random rand = new Random();

    // Helper wrapper to reuse existing FloatingChromosome structure
    private static class FloatingChromosomeFromGenes extends FloatingChromosome {
        public FloatingChromosomeFromGenes(ArrayList<Gene<Double>> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Uniform Mutation ---
    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome, double mutationRate) {
        ArrayList<Gene<Double>> mutatedGenes = new ArrayList<>();

        for (Gene<Double> gene : chromosome.getGenes()) {
            Gene<Double> newGene = gene.copy();

            if (rand.nextDouble() < mutationRate) {
                // Apply uniform mutation: add random delta between [-1, +1]
                double delta = (rand.nextDouble() * 2 - 1);
                newGene.setValue(newGene.getValue() + delta);
            }

            mutatedGenes.add(newGene);
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }
}
