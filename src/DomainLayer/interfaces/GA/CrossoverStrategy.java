package DomainLayer.interfaces.GA;

import java.util.List;
import DomainLayer.entities.GA.Chromosome;

public interface CrossoverStrategy<G, T extends Chromosome<G>> {

    List<T> crossOver(List<T> parents);
    List<T> getNextGeneration();

}
