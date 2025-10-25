package InfrastructureLayer.mutation;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.BinaryChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Binary_ExtendedBitFlipping implements MutationStrategy<Integer>{

    private final Random rand = new Random();

    // Helper class:  wrapper to reuse BinaryChromosome structure
    private static class BinaryChromosomeFromGenes extends BinaryChromosome {
        public BinaryChromosomeFromGenes(List<Integer> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }


    // --- Extended Bit-Flipping Mutation Method Will Add Soon ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
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


