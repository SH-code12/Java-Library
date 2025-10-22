package InfrastructureLayer.replacement;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.ReplacementStrategy;

import java.util.List;

// in this algorithem you replace all parent generation with children
public class GenerationalReplacement <T extends Chromosome> implements ReplacementStrategy<T> {
    @Override
    public Population<T> replace(Population<T> parents, List<T> children) {

        Population<T> newGeneration = new Population<>(children);


        return newGeneration;
    }
}
