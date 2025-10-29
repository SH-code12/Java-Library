package InfrastructureLayer.mutation;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;
import InfrastructureLayer.chromosome.BinaryChromosome;
import DomainLayer.interfaces.MutationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Binary_ExtendedBitFlipping implements MutationStrategy<Integer> {

    private final Random rand = new Random();

    // Helper class — same idea, now wrapping BinaryChromosome
    private static class BinaryChromosomeFromGenes extends BinaryChromosome {
        public BinaryChromosomeFromGenes(List<Gene<Integer>> genes) {
            super(genes.size());
            setGenes(genes);
        }
    }

    // --- Extended Bit-Flipping Mutation (logic unchanged) ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        List<Gene<Integer>> genes = new ArrayList<>(chromosome.getGenes());

        if (rand.nextDouble() < mutationRate && genes.size() > 2) {
            int start = rand.nextInt(genes.size());
            int blockSize = rand.nextInt(Math.max(1, genes.size() / 4)) + 1;

            for (int i = 0; i < blockSize; i++) {
                int index = (start + i) % genes.size();
                Gene<Integer> currentGene = genes.get(index);
                int flipped = currentGene.getValue() == 0 ? 1 : 0;
                genes.set(index, new Gene<>(flipped));
            }
        }

        return new BinaryChromosomeFromGenes(genes);
    }
}
