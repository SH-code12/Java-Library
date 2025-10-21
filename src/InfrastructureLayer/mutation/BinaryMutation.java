package InfrastructureLayer.mutation;
//// Two Methods

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.BinaryChromosome;

public class BinaryMutation implements MutationStrategy<Integer> {
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
    public Chromosome<Integer> mutateFirstMethod(Chromosome<Integer> chromosome, double mutationRate) {
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

    // --- Extended Bit-Flipping Mutation Method Will Add Soon ---
    @Override
    public Chromosome<Integer> mutateSecondMethod(Chromosome<Integer> chromosome, double mutationRate) {
        List<Integer> genes = new ArrayList<>(chromosome.getGenes());

        if (rand.nextDouble() < mutationRate && genes.size() > 2) {
            int start = rand.nextInt(genes.size());
            int blockSize = rand.nextInt(Math.max(1, genes.size() / 4)) + 1;

            for (int i = 0; i < blockSize; i++) {
                int index = (start + i) % genes.size();
                genes.set(index, genes.get(index) == 0 ? 1 : 0);
            }
        }

        return new BinaryChromosomeFromGenes(genes);

    }

}

