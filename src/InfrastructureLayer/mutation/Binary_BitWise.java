package InfrastructureLayer.mutation;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.BinaryChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Binary_BitWise implements MutationStrategy<Integer> {

    private final Random rand = new Random();

    // Helper inner class to reuse BinaryChromosome structure
    private static class BinaryChromosomeFromGenes extends BinaryChromosome {
        public BinaryChromosomeFromGenes(List<Gene<Integer>> genes) {
            super(genes.size());
            setGenes(genes);
        }
    }

    // --- Bit-wise Mutation Method (logic unchanged) ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        List<Gene<Integer>> oldGenes = chromosome.getGenes();
        List<Gene<Integer>> newGenes = new ArrayList<>();

        for (Gene<Integer> gene : oldGenes) {
            int value = gene.getValue();
            if (rand.nextDouble() < mutationRate) {
                newGenes.add(new Gene<>(value == 0 ? 1 : 0)); // Flip bit
            } else {
                newGenes.add(new Gene<>(value));
            }
        }

        return new BinaryChromosomeFromGenes(newGenes);
    }
}
