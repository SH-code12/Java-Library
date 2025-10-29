import ApplicationLayer.services.GeneticAlgorithm;
import DomainLayer.entities.GAConfig;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.*;
import InfrastructureLayer.crossover.NPointCrossover;
import InfrastructureLayer.crossover.OnePointCrossover;
import InfrastructureLayer.crossover.UniformCrossover;
import InfrastructureLayer.fitness.TimetableFitnessFunction;
import InfrastructureLayer.mutation.Integer_RandomReset;
import InfrastructureLayer.mutation.Integer_Swap;
import InfrastructureLayer.replacement.ElitismReplacement;
import InfrastructureLayer.replacement.GenerationalReplacement;
import InfrastructureLayer.replacement.SteadyStateReplacement;
import InfrastructureLayer.selection.RouletteWheelSelection;
import InfrastructureLayer.selection.TournamentSelection;
import PresentationLayer.timetable.TimetableChromosome;
import PresentationLayer.timetable.TimeTablePhenoType;
import PresentationLayer.timetable.entity.Lecture;
import PresentationLayer.timetable.entity.Room;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        // -----------------------------------------
        // Step 1: Get GA Configuration from User
        // -----------------------------------------
        System.out.println("=== Genetic Algorithm Configuration ===");

        System.out.print("Enter Population Size: ");
        int populationSize = input.nextInt();

        System.out.print("Enter Number of Generations: ");
        int generations = input.nextInt();

        //  Random Crossover Rate between 0.05 and 0.3
        double crossoverRate = 0.05 + (0.25 * rand.nextDouble());
        System.out.printf("Random Crossover Rate Selected: %.3f%n", crossoverRate);

        //  Random Mutation Rate between 0.05 and 0.3
        double mutationRate = 0.05 + (0.25 * rand.nextDouble());
        System.out.printf("Random Mutation Rate Selected: %.3f%n", mutationRate);


        // -----------------------------------------
        // Step 2: Choose Strategies
        // -----------------------------------------
        System.out.println("\nChoose Selection Strategy:");
        System.out.println("1. Roulette Wheel");
        System.out.println("2. Tournament");
        int selectionChoice = input.nextInt();

        System.out.println("\nChoose Crossover Strategy:");
        System.out.println("1. One Point");
        System.out.println("2. N-Point");
        System.out.println("3. Uniform");
        int crossoverChoice = input.nextInt();

        System.out.println("\nChoose Mutation Strategy:");
        System.out.println("1. Integer Swap");
        System.out.println("2. Integer Random Reset");
        int mutationChoice = input.nextInt();

        System.out.println("\nChoose Replacement Strategy:");
        System.out.println("1. Generational Replacement");
        System.out.println("2. Elitism Replacement");
        System.out.println("3. SteadyState Replacement");

        int replacementChoice = input.nextInt();

        // -----------------------------------------
        // Step 3: Create Example Timetable Phenotype
        // -----------------------------------------
        List<Lecture> lectures = Arrays.asList(
                new Lecture(0, "Artificial Intelligence", "Dr. Hany", 2, 60, "AI101"),
                new Lecture(1, "Machine Learning", "Dr. Reem", 3, 70, "ML102"),
                new Lecture(2, "Operating Systems", "Dr. Tarek", 3, 80, "OS103"),
                new Lecture(3, "Database Systems", "Dr. Nada", 2, 75, "DB104"),
                new Lecture(4, "Data Structures", "Dr. Salma", 2, 50, "DS105"),
                new Lecture(5, "Computer Networks", "Dr. Omar", 3, 65, "CN106"),
                new Lecture(6, "Software Engineering", "Dr. Heba", 2, 55, "SE107"),
                new Lecture(7, "Cyber Security", "Dr. Ahmed", 3, 60, "CS108"),
                new Lecture(8, "Cloud Computing", "Dr. Yara", 2, 45, "CC109"),
                new Lecture(9, "Human Computer Interaction", "Dr. Laila", 2, 50, "HCI110")
        );

        List<Room> rooms = Arrays.asList(
                new Room("Room A", 100, 6, 3),
                new Room("Room B", 80, 6, 3),
                new Room("Room C", 60, 6, 3),
                new Room("Room D", 40, 6, 3)
        );

//        List<Lecture> lectures = Arrays.asList(
//                new Lecture(0, "AI", "Dr. Hany", 2, 60, "AI101"),
//                new Lecture(1, "ML", "Dr. Reem", 2, 40, "ML102"),
//                new Lecture(2, "OS", "Dr. Tarek", 3, 80, "OS103"),
//                new Lecture(3, "DB", "Dr. Nada", 2, 70, "DB104")
//        );
//
//        List<Room> rooms = Arrays.asList(
//                new Room("Room A", 80, 6, 3),
//                new Room("Room B", 60, 6, 3)
//        );

        List<Integer> slots = Arrays.asList(8, 10, 12, 14, 16);
        TimeTablePhenoType phenotype = new TimeTablePhenoType(lectures, rooms, slots);

        // -----------------------------------------
        // Step 4: Initialize Population
        // -----------------------------------------
        List<TimetableChromosome> individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            TimetableChromosome chromosome = new TimetableChromosome(phenotype);
            individuals.add(chromosome);
        }
        Population<TimetableChromosome> population = new Population<>(individuals);

        // -----------------------------------------
        // Step 5: Build GA Components Dynamically
        // -----------------------------------------
        FitnessFunction<TimetableChromosome> fitnessFn = new TimetableFitnessFunction<>();

        // Selection
        SelectionStrategy<TimetableChromosome> selectionStrategy;
        switch (selectionChoice) {
            case 2 -> selectionStrategy = new TournamentSelection<>(3);

            default -> selectionStrategy = new RouletteWheelSelection<>(individuals);
        }

        // Crossover
        CrossoverStrategy<Integer, TimetableChromosome> crossoverStrategy;
        switch (crossoverChoice) {
            case 2 -> crossoverStrategy = new NPointCrossover<>(individuals);
            case 3 -> crossoverStrategy = new UniformCrossover<>(individuals);
            default -> crossoverStrategy = new OnePointCrossover<>(individuals);
        }

        // Mutation
        MutationStrategy<Integer> mutationStrategy;
        switch (mutationChoice) {
            case 2 -> mutationStrategy = new Integer_RandomReset();
            default -> mutationStrategy = new Integer_Swap();
        }

        // Replacement
        ReplacementStrategy<TimetableChromosome> replacementStrategy;
        switch (replacementChoice) {
            case 2 -> replacementStrategy = new ElitismReplacement<>(5); // keep top 5 parents, for example
            case 3 -> replacementStrategy = new SteadyStateReplacement<>(5); // replace 5 weakest parents
            default -> replacementStrategy = new GenerationalReplacement<>();
        }


        // Step 6: Initialize GA Config (random rates handled inside)
        // -----------------------------------------
        GAConfig config = new GAConfig(
                populationSize,
                0,   // 0 → means random crossover rate
                 0,   // 0 → means random mutation rate
                generations
        );

        // Initialize GA with GAConfig
        GeneticAlgorithm<Integer, TimetableChromosome> ga = new GeneticAlgorithm<>(
                config,
                fitnessFn,
                selectionStrategy,
                crossoverStrategy,
                mutationStrategy,
                replacementStrategy,
                population
        );


        // -----------------------------------------
        // Step 7: Run GA
        // -----------------------------------------
        System.out.println("\nRunning Genetic Algorithm...\n");

        TimetableChromosome best = ga.run();

        System.out.println("\n=== Final Best Timetable ===");
        System.out.println(best);
        System.out.println("Final Best Fitness: " + ga.getHigherFitValue());
    }
}
