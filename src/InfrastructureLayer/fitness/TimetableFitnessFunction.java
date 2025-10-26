package InfrastructureLayer.fitness;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.FitnessFunction;

public class TimetableFitnessFunction<T extends Chromosome<?>> implements FitnessFunction<T> {
    @Override
    public double evaluate(Chromosome<T> chromosome) {
        chromosome.calculateFitnessValue();
        return chromosome.getFitness();
    }
}
