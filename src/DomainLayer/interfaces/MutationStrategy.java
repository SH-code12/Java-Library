package DomainLayer.interfaces;

import DomainLayer.entities.Chromosome;

public interface MutationStrategy<T > {

    Chromosome<T> mutate(Chromosome<T> chromosome, double mutationRate);
}

