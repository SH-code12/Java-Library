package InfrastructureLayer.mutation;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.BinaryChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//// Two Methods

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.BinaryChromosome;

public class Binary_BitWise implements MutationStrategy<Integer> {


    private final Random rand = new Random();

    // Helper class:  wrapper to reuse BinaryChromosome structure
    private static class BinaryChromosomeFromGenes extends BinaryChromosome {
        public BinaryChromosomeFromGenes(List<Integer> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Bit-wise Mutation Method ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        List<Integer> oldGenes = chromosome.getGenes();
        List<Integer> newGenes = new ArrayList<>();

        for (int bit : oldGenes) {
            if (rand.nextDouble() < mutationRate) {
                newGenes.add(bit == 0 ? 1 : 0); // Flip bit
            } else {
                newGenes.add(bit);
            }
        }

        return new BinaryChromosomeFromGenes(newGenes);
    }


}


