package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import java.util.Map;
import DomainLayer.entities.Fuzzy.FuzzySet;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

/**
 * Weighted Average defuzzifier (general Mamdani variant):
 * For each output set i:
 *   - compute height h_i = max_x mu_i(x)
 *   - compute set representative z_i = centroid_of_set = sum_x x * mu_i(x) / sum_x mu_i(x)
 * Then return weighted average: sum_i (h_i * z_i) / sum_i h_i
 *
 * If sum of heights == 0 -> returns midpoint of output domain.
 */
public class SugenoWeightedAverage implements DefuzzificationStrategy {

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
            double degree = e.getValue();
            FuzzySet set = outputVariable.getFuzzySets().get(e.getKey());
            if (set == null) {
                continue;
            }

            double zi;
            if (set.getMf() instanceof Triangular tri) {
                zi = (tri.getA() + tri.getB() + tri.getC())/3.0;

            } else if (set.getMf() instanceof Trapezoidal trap) {
                zi = (trap.getA() + 2*trap.getB() + 2*trap.getC() + trap.getD())/6.0;

            } else {
                zi = (outputVariable.getMin() + outputVariable.getMax())/2.0;
            }

            numerator += degree * zi;
            denominator += degree;
        }

        return denominator == 0.0 ? midpoint(outputVariable) : numerator / denominator;
    }
}



