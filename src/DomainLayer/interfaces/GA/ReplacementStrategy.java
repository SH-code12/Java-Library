package DomainLayer.interfaces.GA;

import DomainLayer.entities.GA.Chromosome;
import DomainLayer.entities.GA.Population;

import java.util.List;

// population  -> chromosomes ->genes
public interface ReplacementStrategy <T extends Chromosome> {
    Population<T> replace(Population<T> parents, List<T> children );
}
