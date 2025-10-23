package InfrastructureLayer.fitness;

import DomainLayer.entities.Chromosome;
import DomainLayer.interfaces.IFitnessFunction;
import InfrastructureLayer.selection.RouletteWheelSelection;

public class FitnessFunction<T extends Chromosome> implements IFitnessFunction<T> {
    @Override
    public double getFitnessVal(Chromosome gene) {
        return gene.getFitness();
    }


}
