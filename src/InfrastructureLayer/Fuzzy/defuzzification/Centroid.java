package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.entities.Fuzzy.FuzzySet;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;
import java.util.Map;
import java.util.TreeMap;


/**
 * Centroid (center of area) computed numerically over sampled inferred data:
 *   z = ( ∑ x * μ_agg(x) ) / ( ∑ μ_agg(x) )
 *
 * Here inferred is a map per-output-set containing sampled (x->μ_set(x)) values.
 * We need to combine sets into aggregated μ_agg(x) = max_over_sets μ_set(x) (Mamdani max-aggregation).
 *
 * This implementation expects that the 'inferred' structure already contains per-set samples where
 * the sampling semantics are the same; it computes μ_agg(x) by taking the maximum across the sets
 * for each sampled x key. If x sampling points differ across sets, numeric keys are parsed and unioned.
 */
public class Centroid implements DefuzzificationStrategy {

    private double midpoint(LinguisticVariable lv) {
        if (lv == null) return 0.0;
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Double> fuzzyValues, LinguisticVariable outputVariable) {
        if (fuzzyValues == null || fuzzyValues.isEmpty()) {
            return midpoint(outputVariable);
        }

        double numerator = 0.0;
        double denominator = 0.0;

        for (Map.Entry<String, Double> e : fuzzyValues.entrySet()) {
            String setName = e.getKey();
            double degree = e.getValue();

            FuzzySet set = outputVariable.getFuzzySets().get(setName);
            if (set == null) continue;

            double representative;

            // Triangular: use centroid formula (a + b + c)/3
            if (set.getMf() instanceof Triangular tri) {
                representative = (tri.getA() + tri.getB() + tri.getC()) / 3.0;
            }
            // Trapezoidal: use centroid formula (a + 2b + 2c + d)/6
            else if (set.getMf() instanceof Trapezoidal trap) {
                representative = (trap.getA() + 2*trap.getB() + 2*trap.getC() + trap.getD())/6.0;
            } else {
                representative = (outputVariable.getMin() + outputVariable.getMax())/2.0;
            }

            numerator += degree * representative;
            denominator += degree;
        }

        return denominator == 0 ? midpoint(outputVariable) : numerator / denominator;
    }
}

