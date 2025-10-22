package InfrastructureLayer.replacement;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.ReplacementStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// in this algorithem you take the best of the parent generation + all children
public class ElitismReplacement <T extends Chromosome> implements ReplacementStrategy<T> {

    private final int replaced;

    public ElitismReplacement(int replaced) {
        this.replaced = replaced;
    }

    @Override
    public Population<T> replace(Population<T> parents, List<T> children) {
        List <T> newGen = new ArrayList<>(parents.getChromosomes());
        newGen.sort((Comparator.comparingDouble(Chromosome::getFitness)));
        int temp = replaced;
        while(temp>0 ){
            temp--;
            newGen.remove(0);
        }
        newGen.addAll(children);
        Population<T> finalSurvivors = new Population<>(newGen);
        return finalSurvivors;

    }
}
