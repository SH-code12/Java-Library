package InfrastructureLayer.mutation;
//// Two Methods

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.chromosome.IntegerChromosome;

public class IntegerMutation implements MutationStrategy<Integer> {
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
    public Chromosome<Integer> mutateFirstMethod(Chromosome<Integer> chromosome, double mutationRate) {
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

    // --- Swap Mutation Method---
    @Override
    public Chromosome<Integer> mutateSecondMethod(Chromosome<Integer> chromosome, double mutationRate) {
        List<Integer> genes = new ArrayList<>(chromosome.getGenes());

        if (rand.nextDouble() < mutationRate && genes.size() > 1) {
            int i = rand.nextInt(genes.size());
            int j = rand.nextInt(genes.size());
            Collections.swap(genes, i, j);
        }

        return new IntegerChromosomeFromGenes(genes);
    }

}

