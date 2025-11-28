package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.entities.Fuzzy.FuzzySet;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

import java.util.Map;

/**
 * Height method (Max-membership principle).
 * For each output fuzzy set compute its height h_i = max_x mu_i(x).
 * If nothing fires, returns midpoint of the output domain.
 */
public class MaxMembershipHeight implements DefuzzificationStrategy {

    private double midpoint(LinguisticVariable lv) {
        if (lv == null){
            return 0.0;
        }
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Double> fuzzyValues, LinguisticVariable outputVariable) {
        if (fuzzyValues == null || fuzzyValues.isEmpty()) {
            return midpoint(outputVariable);
        }

        String bestSet = null;
        double maxDegree = -1.0;

        for (Map.Entry<String, Double> e : fuzzyValues.entrySet()) {
            if (e.getValue() > maxDegree) {
                maxDegree = e.getValue();
                bestSet = e.getKey();
            }
        }

        if (bestSet == null) {
            return midpoint(outputVariable);
        }

        FuzzySet set = outputVariable.getFuzzySets().get(bestSet);
        if (set == null) {
            return midpoint(outputVariable);
        }

        if (set.getMf() instanceof Triangular tri){
            return tri.getB(); // peak
        }
        if (set.getMf() instanceof Trapezoidal trap){
            return (trap.getB() + trap.getC())/2.0; // center of plateau
        }

        return midpoint(outputVariable);
    }
}


