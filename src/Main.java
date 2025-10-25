import ApplicationLayer.services.GeneticAlgorithm;
import DomainLayer.entities.Population;
import InfrastructureLayer.chromosome.IntegerChromosome;
import InfrastructureLayer.crossover.NPointCrossover;
import InfrastructureLayer.fitness.FitnessFunction;
import InfrastructureLayer.mutation.Integer_Swap;
import InfrastructureLayer.replacement.GenerationalReplacement;
import InfrastructureLayer.selection.RouletteWheelSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final int population_size = 4;
    private static final int generations = 5;
    private static   final int  gene_length = 8;
    private static final double mutation_rate = 0.2;
    private  static final int  lowerBound =0;
    private static final int upperBound = 9;


    public static void main(String[] args) {

        FitnessFunction fitnessFunction = new FitnessFunction();
        IntegerChromosome myChromosome = new IntegerChromosome(4,2,1);
        IntegerChromosome myChromosome2 = new IntegerChromosome(4, 3, 1);
        IntegerChromosome myChromosome3 = new IntegerChromosome(4, 3, 1);IntegerChromosome myChromosome4 = new IntegerChromosome(4, 3, 1);
        IntegerChromosome myChromosome5 = new IntegerChromosome(4, 2, 1);
        IntegerChromosome myChromosome6 = new IntegerChromosome(4, 2, 1);
        IntegerChromosome myChromosome7 = new IntegerChromosome(4, 2, 1);
      myChromosome.initializeGenes();myChromosome2.initializeGenes();myChromosome3.initializeGenes();myChromosome4.initializeGenes();
        myChromosome5.initializeGenes();myChromosome6.initializeGenes();myChromosome7.initializeGenes();
        List<IntegerChromosome> searchSpace = new ArrayList<>();
        searchSpace.addAll(Arrays.asList(myChromosome, myChromosome2, myChromosome3
                ,myChromosome4,myChromosome5,myChromosome6,myChromosome7
        ));


        RouletteWheelSelection rouletteWheelSelection = new RouletteWheelSelection<>(searchSpace);
        NPointCrossover nPointCrossover = new NPointCrossover<>(searchSpace);
        Integer_Swap integerMutation = new Integer_Swap();

        GenerationalReplacement generationalReplacement = new
                GenerationalReplacement();

        GeneticAlgorithm<Integer,IntegerChromosome> ga = new GeneticAlgorithm<>();
        ga.setPopulationSz(population_size);
        ga.setGenerationSz(generations);
        ga.setMutationRate(mutation_rate);
        ga.setPop(new Population<>(searchSpace));

        ga.setFitnessFn(fitnessFunction);
        ga.setSelectionStrategy(rouletteWheelSelection);
        ga.setCrossoverStrategy(nPointCrossover);

        ga.setMutationStrategy(integerMutation);
        ga.setReplacementStrategy(generationalReplacement);

        try {
            IntegerChromosome initialBest = ga.getBest();
            System.out.println("\nInitial Best Solution (before run): " + initialBest);

            IntegerChromosome finalBest = ga.run();

            System.out.println("\n--- GA Run Complete After " + generations + " Generations ---");
            System.out.println("Final Best Solution Found: " + finalBest);
            System.out.println("Final Highest Fitness: " + String.format("%.2f", ga.getHigherFitValue()));

        } catch (IllegalStateException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        }


    }
}