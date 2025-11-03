package InfrastructureLayer.GA.mutation;

import java.util.ArrayList;
import java.util.Random;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Gene;
import DomainLayer.interfaces.MutationStrategy;
import InfrastructureLayer.GA.chromosome.FloatingChromosome;

public class Floating_NonUniform implements MutationStrategy<Double> {
    private final Random rand = new Random();
    private final double b;  // Controls non-uniformity
    private final int currentGen;
    private final int maxGen;

    public Floating_NonUniform(int currentGen, int maxGen, double b) {
        this.currentGen = currentGen;
        this.maxGen = maxGen;
        this.b = b;
    }

    // Helper wrapper class
    private static class FloatingChromosomeFromGenes extends FloatingChromosome {
        public FloatingChromosomeFromGenes(ArrayList<Gene<Double>> genes) {
            super(1, 1, 1);
            setGenes(genes);
        }
    }
    // --- Non-Uniform Mutation ---
    @Override
    public Chromosome<Double> mutate(Chromosome<Double> chromosome, double mutationRate) {
        ArrayList<Gene<Double>> mutatedGenes = new ArrayList<>();

        double LBi = 0.0;
        double UBi = 10.0;

        for (Gene<Double> gene : chromosome.getGenes()) {
            Gene<Double> newGene = gene.copy();
            double Xi = newGene.getValue();

            if (rand.nextDouble() < mutationRate) {

                // Step a & b
                double deltaL = Xi - LBi;
                double deltaU = UBi - Xi;

                // Step c
                double r1 = rand.nextDouble();
                double y = (r1 <= 0.5) ? deltaL : deltaU;

                // Step d
                double r = rand.nextDouble();
                double deltaTY = y * (1 - Math.pow(r, Math.pow((1.0 - (double) currentGen / maxGen), b)));

                // Step e
                if (y == deltaL) {
                    Xi = Xi - deltaTY;
                } else {
                    Xi = Xi + deltaTY;
                }

                // Clamp to bounds
                Xi = Math.max(LBi, Math.min(UBi, Xi));
                newGene.setValue(Xi);
            }

            mutatedGenes.add(newGene);
        }

        return new FloatingChromosomeFromGenes(mutatedGenes);
    }
}

