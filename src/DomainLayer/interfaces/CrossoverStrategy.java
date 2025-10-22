package DomainLayer.interfaces;

import java.util.List;
import DomainLayer.entities.Chromosome;

public interface CrossoverStrategy<G, T extends Chromosome<G>> {
    void crossOver();
    List<T> getNextGeneration();
// Interfaces Required
public interface CrossoverStrategy {
}
