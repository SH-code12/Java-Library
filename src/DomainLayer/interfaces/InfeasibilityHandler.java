package DomainLayer.interfaces;

import DomainLayer.entities.Chromosome;

/**
 * Defines how to handle infeasible chromosomes (e.g., constraint violations).
 * Implementations can repair, penalize, or discard chromosomes.
 */
public interface InfeasibilityHandler<T> {
    Chromosome<T> repair(Chromosome<T> chromosome);
}
