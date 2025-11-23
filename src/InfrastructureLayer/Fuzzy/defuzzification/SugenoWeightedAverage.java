package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import java.util.Map;


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

    private static final double EPS = 1e-12;

    private double midpoint(LinguisticVariable lv) {
        if (lv == null) {
            return 0.0;
        }
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Map<String, Double>> inferred, LinguisticVariable outputVariable) {
        if (inferred == null || inferred.isEmpty()) return midpoint(outputVariable);

        double numerator = 0.0;
        double denominator = 0.0;

        for (Map<String, Double> samples : inferred.values()) {
            if (samples == null || samples.isEmpty()){
                continue;
            }

            double maxMu = Double.NEGATIVE_INFINITY;
            double centroidNum = 0.0;
            double centroidDen = 0.0;

            for (Map.Entry<String, Double> e : samples.entrySet()) {
                Double mu = e.getValue();
                if (mu == null){
                    continue;
                }
                double x;
                try {
                    x = Double.parseDouble(e.getKey());
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (mu > maxMu){
                    maxMu = mu;
                }
                centroidNum += x * mu;
                centroidDen += mu;
            }

            if (maxMu <= 0.0 || maxMu == Double.NEGATIVE_INFINITY) {
                continue;
            }

            double zi;
            if (centroidDen > EPS){
                zi = centroidNum / centroidDen;
            }
            else {
                // fallback representative: choose mid of output domain
                zi = midpoint(outputVariable);
            }

            numerator += maxMu * zi;
            denominator += maxMu;
        }

        if (denominator == 0.0) {
            return midpoint(outputVariable);
        }
        return numerator / denominator;
    }
}


