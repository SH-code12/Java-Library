package InfrastructureLayer.GA.replacement;

import DomainLayer.entities.GA.Chromosome;
import DomainLayer.entities.GA.Population;
import DomainLayer.interfaces.GA.ReplacementStrategy;

import java.util.List;

// in this algorithem you replace all parent generation with children
public class GenerationalReplacement <T extends Chromosome> implements ReplacementStrategy<T> {
    @Override
    public Population<T> replace(Population<T> parents, List<T> children) {

        Population<T> newGeneration = new Population<>(children);


        return newGeneration;
    }
}
