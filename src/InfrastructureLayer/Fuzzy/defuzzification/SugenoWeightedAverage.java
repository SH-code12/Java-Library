package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SugenoWeightedAverage implements DefuzzificationStrategy {

    // keep a small private helper that does the final weighted average
    private double defuzzifyWeighted(List<Integer> constantOutputs, List<Double> firingStrengths) {
        double sumWeightedOutputs = 0.0;
        double sumWeights = 0.0;

        for (int i = 0; i < constantOutputs.size(); i++) {
            sumWeightedOutputs += constantOutputs.get(i) * firingStrengths.get(i);
            sumWeights += firingStrengths.get(i);
        }

        return sumWeights == 0 ? 0.0 : sumWeightedOutputs / sumWeights;
    }

    @Override
    public double defuzzify(Map<String, Double> inferred, LinguisticVariable outputVariable) {
        List<Integer> constantOutputs = new ArrayList<>();
        List<Double> firingStrengths = new ArrayList<>();

        for (Map.Entry<String, Double> entry : inferred.entrySet()) {
            String setName = entry.getKey();
            double strength = entry.getValue();

            // skip zero-strength rules (optional)
            if (strength <= 0.0) continue;

            FuzzySet set = outputVariable.getFuzzySets().get(setName);
            if (set == null) continue;

            double zi;
            if (set.getMf() instanceof Triangular tri) {
                // centroid for triangle: (a + b + c)/3
                zi = (tri.getA() + tri.getB() + tri.getC()) / 3.0;
            } else if (set.getMf() instanceof Trapezoidal trap) {
                // centroid approximation for trapezoid: weighted average
                zi = (trap.getA() + 2 * trap.getB() + 2 * trap.getC() + trap.getD()) / 6.0;
            } else {
                // fallback: mid of output range
                zi = (outputVariable.getMin() + outputVariable.getMax()) / 2.0;
            }

            // If your output domain is integer-based, you may want to round or cast.
            // Preserve decimals if needed; Sugeno usually uses numeric consequents.
            constantOutputs.add((int) Math.round(zi));
            firingStrengths.add(strength);
        }

        return defuzzifyWeighted(constantOutputs, firingStrengths);
    }

}
