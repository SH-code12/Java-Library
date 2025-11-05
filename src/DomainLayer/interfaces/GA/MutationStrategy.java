package DomainLayer.interfaces.GA;

import DomainLayer.entities.GA.Chromosome;

public interface MutationStrategy<T > {

    Chromosome<T> mutate(Chromosome<T> chromosome, double mutationRate);
}

