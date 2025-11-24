package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;

import java.util.Map;

/**
 * Height method (Max-membership principle).
 *
 * For each output fuzzy set compute its height h_i = max_x mu_i(x).
 * Choose the single z* that has the global maximum membership (the set with largest h_i
 * and the x that attained it). If multiple sets tie, the current implementation picks the
 * first encountered argmax (deterministic iteration order of inferred map).
 *
 * If nothing fires, returns midpoint of the output domain.
 */
public class MaxMembershipHeight implements DefuzzificationStrategy {

    private static final double EPS = 1e-12;

    private double midpoint(LinguisticVariable lv) {
        if (lv == null) {
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
        Double bestX = null;

        for (Map<String, Double> sampleMap : inferred.values()) {
            if (sampleMap == null) {
                continue;
            }
            for (Map.Entry<String, Double> e : sampleMap.entrySet()) {
                Double mu = e.getValue();
                if (mu == null) {
                    continue;
                }
                if (mu > globalMax + EPS) {
                    globalMax = mu;
                    try {
                        bestX = Double.parseDouble(e.getKey());
                    }
                    catch (NumberFormatException ex) {
                        bestX = null;
                    }
                }
            }
        }

        if (globalMax <= 0.0 || bestX == null || bestX.isNaN()){
            return midpoint(outputVariable);
        }
        return bestX;
    }

}

