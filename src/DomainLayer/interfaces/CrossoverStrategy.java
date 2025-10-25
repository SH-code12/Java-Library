package DomainLayer.interfaces;

import java.util.List;
import DomainLayer.entities.Chromosome;

public interface CrossoverStrategy<G, T extends Chromosome<G>> {

    List<T> crossOver(List<T> parents);
    List<T> getNextGeneration();
}
