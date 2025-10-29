package InfrastructureLayer.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import InfrastructureLayer.chromosome.IntegerChromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;

public class Integer_Swap implements MutationStrategy<Integer> {

    private final Random rand = new Random();

    // Helper wrapper to easily reuse existing structure
    private static class IntegerChromosomeFromGenes extends IntegerChromosome {
        public IntegerChromosomeFromGenes(ArrayList<Gene<Integer>> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Swap Mutation Method ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        // Clone genes to avoid modifying the original chromosome directly
        ArrayList<Gene<Integer>> genes = new ArrayList<>();
        for (Gene<Integer> g : chromosome.getGenes()) {
            genes.add(g.copy());
        }

        if (rand.nextDouble() < mutationRate && genes.size() > 1) {
            int i = rand.nextInt(genes.size());
            int j = rand.nextInt(genes.size());
            Collections.swap(genes, i, j);
        }

        return new IntegerChromosomeFromGenes(genes);
    }
}
