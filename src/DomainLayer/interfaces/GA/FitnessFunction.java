package DomainLayer.interfaces.GA;

import DomainLayer.entities.GA.Chromosome;

/**
 * Defines a contract for computing the fitness of a chromosome.
 * Users can define any domain-specific fitness logic here.
 */
public interface FitnessFunction<T> {
    double evaluate(Chromosome<T> chromosome);

}
