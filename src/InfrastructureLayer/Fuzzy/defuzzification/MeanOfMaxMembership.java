package InfrastructureLayer.Fuzzy.defuzzification;


import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

import java.util.*;

/**
 * Mean of Maximum (MoM) defuzzifier.
 *
 * Expects `inferred` to be a map of outputSetName -> (xString -> membership).
 * It finds the global maximum membership across all sampled x values (all sets),
 * then collects all x where membership >= max - EPS, and returns their arithmetic mean.
 *
 * If no samples exist, returns the mid-point of the output variable domain.
 */
public class MeanOfMaxMembership implements DefuzzificationStrategy {

    private double midpoint(LinguisticVariable lv) {
        if (lv == null) {
            return 0.0;
        }
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Double> fuzzyValues, LinguisticVariable outputVariable) {
        if (fuzzyValues == null || fuzzyValues.isEmpty()){
            return midpoint(outputVariable);
        }

        double maxDegree = Collections.max(fuzzyValues.values());

        List<Double> peaks = new ArrayList<>();
        for (Map.Entry<String, Double> e : fuzzyValues.entrySet()) {

            if (Math.abs(e.getValue() - maxDegree) < 1e-9) {
                FuzzySet set = outputVariable.getFuzzySets().get(e.getKey());
                if (set == null) {
                    continue;
                }
                if (set.getMf() instanceof Triangular tri){
                    peaks.add(tri.getB());
                }
                else if (set.getMf() instanceof Trapezoidal trap){
                    peaks.add((trap.getB() + trap.getC())/2.0);
                }
                else peaks.add((outputVariable.getMin() + outputVariable.getMax())/2.0);
            }
        }

        return peaks.stream().mapToDouble(Double::doubleValue).average().orElse(midpoint(outputVariable));
    }
}

