package InfrastructureLayer.Fuzzy.defuzzification;


import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;

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

    // small epsilon to tolerate floating point rounding
    private static final double EPS = 1e-9;

    private double midpoint(LinguisticVariable lv) {
        if (lv == null){
            return 0.0;
        }
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Map<String, Double>> inferred, LinguisticVariable outputVariable) {
        if (inferred == null || inferred.isEmpty()) {
            return midpoint(outputVariable);
        }

        double globalMax = Double.NEGATIVE_INFINITY;
        // First pass: find global maximum membership
        for (Map<String, Double> sampleMap : inferred.values()) {
            for (double mu : sampleMap.values()) {
                if (mu > globalMax) {
                    globalMax = mu;
                }
            }
        }

        if (globalMax <= 0.0 || globalMax == Double.NEGATIVE_INFINITY) {
            // nothing fired; return sensible default (midpoint)
            return midpoint(outputVariable);
        }

        // Second pass: gather all x values whose membership is (approximately) the global max
        List<Double> xs = new ArrayList<>();
        for (Map<String, Double> sampleMap : inferred.values()) {
            for (Map.Entry<String, Double> e : sampleMap.entrySet()) {
                double mu = e.getValue();
                if (Math.abs(mu - globalMax) <= EPS) {
                    try {
                        double x = Double.parseDouble(e.getKey());
                        xs.add(x);
                    } catch (NumberFormatException ex) {
                        // ignore entries that are not numeric (shouldn't happen for sampled x)
                    }
                }
            }
        }

        if (xs.isEmpty()) {
            return midpoint(outputVariable);
        }

        // compute arithmetic mean of the xs
        double sum = 0.0;
        for (double v : xs){
            sum += v;
        }
        return sum / xs.size();
    }

}

