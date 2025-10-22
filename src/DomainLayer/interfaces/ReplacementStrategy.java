package DomainLayer.interfaces;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Population;

import java.util.List;

// population  -> chromosomes ->genes
public interface ReplacementStrategy <T extends Chromosome> {
    Population<T> replace(Population<T> parents, List<T> children );
}
