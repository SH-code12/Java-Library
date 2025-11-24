package InfrastructureLayer.Fuzzy.defuzzification;

import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
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
        if (lv == null) {
            return 0.0;
        }
        return (lv.getMin() + lv.getMax()) / 2.0;
    }

    @Override
    public double defuzzify(Map<String, Map<String, Double>> inferred, LinguisticVariable outputVariable) {
        if (inferred == null || inferred.isEmpty()){
            return midpoint(outputVariable);
        }

        // Build aggregated map: x -> max_mu
        Map<Double, Double> agg = new TreeMap<>();
        for (Map<String, Double> samples : inferred.values()) {
            if (samples == null){
                continue;
            }
            for (Map.Entry<String, Double> e : samples.entrySet()) {
                try {
                    double x = Double.parseDouble(e.getKey());
                    double mu = e.getValue() == null ? 0.0 : e.getValue();
                    agg.put(x, Math.max(agg.getOrDefault(x, 0.0), mu));
                } catch (NumberFormatException ex) {
                    // skip non-numeric keys
                }
            }
        }

        if (agg.isEmpty()){
            return midpoint(outputVariable);
        }

        double num = 0.0;
        double den = 0.0;
        for (Map.Entry<Double, Double> e : agg.entrySet()) {
            double x = e.getKey();
            double mu = e.getValue();
            num += x * mu;
            den += mu;
        }
        return den == 0.0 ? midpoint(outputVariable) : num / den;
    }

}
