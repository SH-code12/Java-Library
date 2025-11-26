package InfrastructureLayer.Fuzzy.defuzzification;

import java.util.List;

public class SugenoWeightedAverage {
    public double defuzzify(List<Integer> constantOutputs, List<Double> firingStrengths) {
       
        double sumWeightedOutputs = 0.0;
        double sumWeights = 0.0;

        for (int i = 0; i < constantOutputs.size(); i++) {
            sumWeightedOutputs += (constantOutputs.get(i)*firingStrengths.get(i));
            sumWeights += firingStrengths.get(i);
        }

        return sumWeightedOutputs / sumWeights;
    }
}
