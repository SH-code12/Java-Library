package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;

import java.util.Map;

/**
 * z = sum(w_i * z_i) / sum(w_i)
 */
public class SugenoWeightedAverage implements DefuzzificationStrategy {

    @Override
    public double defuzzify(Map<String, Double> inferred, LinguisticVariable outputVariable) {
        if (inferred == null || inferred.isEmpty()) {
            // fallback to midpoint of output domain
            return (outputVariable.getMin() + outputVariable.getMax()) / 2.0;
        }


        double numerator = 0.0;
        double denominator = 0.0;

        for (Map.Entry<String, Double> entry : inferred.entrySet()) {
            String setName = entry.getKey();
            double strength = entry.getValue();

            if (strength <= 0.0) continue;

            FuzzySet set = outputVariable.getFuzzySets().get(setName);
            if (set == null || set.getSugenoOutput() == null) continue; // numeric consequent

            double zi = set.getSugenoOutput(); // numeric output of this fuzzy set
            numerator += strength * zi;
            denominator += strength;
        }

        return denominator == 0.0 ? (outputVariable.getMin() + outputVariable.getMax()) / 2.0
                : numerator / denominator;


    }
}
