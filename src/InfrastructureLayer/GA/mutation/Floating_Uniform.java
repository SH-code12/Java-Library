package InfrastructureLayer.GA.mutation;

import java.util.ArrayList;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import InfrastructureLayer.GA.chromosome.FloatingChromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;

public class Floating_Uniform implements MutationStrategy<Double> {
    private final Random rand = new Random();

    // Helper wrapper to reuse FloatingChromosome
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

        // Assuming known bounds
        double LBi = 0.0;
        double UBi = 10.0;

        for (Gene<Double> gene : chromosome.getGenes()) {
            Gene<Double> newGene = gene.copy();
            double Xi = newGene.getValue();

            // Only mutate if random < mutationRate
            if (rand.nextDouble() < mutationRate) {

                // Step (a, b)
                double deltaL = Xi - LBi;
                double deltaU = UBi - Xi;

                // Step (c)
                double r1 = rand.nextDouble();
                double delta, r2;

                if (r1 <= 0.5) {
                    // Move left
                    delta = deltaL;
                    r2 = rand.nextDouble() * delta;
                    Xi = Xi - r2;
                } else {
                    // Move right
                    delta = deltaU;
                    r2 = rand.nextDouble() * delta;
                    Xi = Xi + r2;
                }

                // Clamp value to bounds
                Xi = Math.max(LBi, Math.min(UBi, Xi));
                newGene.setValue(Xi);
            }

            mutatedGenes.add(newGene);
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }
}
