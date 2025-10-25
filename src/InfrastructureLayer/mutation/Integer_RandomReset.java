package InfrastructureLayer.mutation;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.IntegerChromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Integer_RandomReset implements MutationStrategy<Integer> {

    private final Random rand = new Random();

    // Helper wrapper to easily reuse existing structure
    private static class IntegerChromosomeFromGenes extends IntegerChromosome {
        public IntegerChromosomeFromGenes(List<Integer> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }

    // --- Bit-wise (Random Reset) Mutation Method ---
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        List<Integer> genes = new ArrayList<>(chromosome.getGenes());
        List<Integer> mutatedGenes = new ArrayList<>();

        for (int value : genes) {
            if (rand.nextDouble() < mutationRate) {
                // Flip bit-like — random new integer (for timetable range 1–max)
                int newValue = rand.nextInt(10) + 1;
                mutatedGenes.add(newValue);
            } else {
                mutatedGenes.add(value);
            }
        }

        return new IntegerChromosomeFromGenes(mutatedGenes);
    }


}


