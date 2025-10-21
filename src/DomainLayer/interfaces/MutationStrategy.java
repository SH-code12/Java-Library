package DomainLayer.interfaces;

import DomainLayer.entities.Chromosome;

public interface MutationStrategy<T> {

    Chromosome<T> mutateFirstMethod(Chromosome<T> chromosome, double mutationRate);

    Chromosome<T> mutateSecondMethod(Chromosome<T> chromosome, double mutationRate);
}

