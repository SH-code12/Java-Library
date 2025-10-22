package InfrastructureLayer.replacement;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.ReplacementStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// in this algorithem you always choose the best from parents and children
public class SteadyStateReplacement <T extends Chromosome> implements ReplacementStrategy<T> {
    private final int replaced;

    public SteadyStateReplacement(int replaced) {
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
        if (children.size() > replaced) {
            children.sort(Comparator.comparingDouble(T::getFitness).reversed());
        }
        newGen.addAll(children.subList(0,Math.min(replaced, children.size())));
        Population<T> finalSurvivors = new Population<>(newGen);
        return finalSurvivors;

    }
}
