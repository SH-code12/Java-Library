package InfrastructureLayer.GA.fitness;

import DomainLayer.entities.GA.Chromosome;
import DomainLayer.interfaces.GA.FitnessFunction;

public class TimetableFitnessFunction<T extends Chromosome<?>> implements FitnessFunction<T> {
    @Override
    public double evaluate(Chromosome<T> chromosome) {
        chromosome.calculateFitnessValue();
        return chromosome.getFitness();
    }
}
