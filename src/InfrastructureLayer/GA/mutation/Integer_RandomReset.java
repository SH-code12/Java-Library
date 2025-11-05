package InfrastructureLayer.GA.mutation;

import java.util.ArrayList;
import java.util.Random;

import DomainLayer.entities.GA.Chromosome;
import DomainLayer.entities.GA.Gene;
import DomainLayer.interfaces.GA.MutationStrategy;
import InfrastructureLayer.GA.chromosome.IntegerChromosome;

public class Integer_RandomReset implements MutationStrategy<Integer> {

    private final Random rand = new Random();

    // Helper wrapper to reuse existing IntegerChromosome structure
    private static class IntegerChromosomeFromGenes extends IntegerChromosome {
        public IntegerChromosomeFromGenes(ArrayList<Gene<Integer>> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Random Reset Mutation Method ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        // Clone and mutate genes safely
        ArrayList<Gene<Integer>> mutatedGenes = new ArrayList<>();

        for (Gene<Integer> gene : chromosome.getGenes()) {
            Gene<Integer> newGene = gene.copy();

            if (rand.nextDouble() < mutationRate) {
                // Assign a new random integer value (for example, 1–10 range)
                newGene.setValue(rand.nextInt(10) + 1);
            }

            mutatedGenes.add(newGene);
        }

        return new IntegerChromosomeFromGenes(mutatedGenes);
    }
}
