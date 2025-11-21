package InfrastructureLayer.Fuzzy.validation;


import DomainLayer.interfaces.Fuzzy.InputValidator;
import DomainLayer.entities.Fuzzy.LinguisticVariable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Throws an exception on missing or out-of-range values.
 */
public class StrictInputValidator implements InputValidator {
    @Override
    public Map<String, Double> validate(Map<String, Double> inputs, Map<String, LinguisticVariable> variables) {
        Map<String, Double> out = new LinkedHashMap<>();
        for (Map.Entry<String, LinguisticVariable> e : variables.entrySet()) {
            String name = e.getKey();
            LinguisticVariable lv = e.getValue();
            if (!inputs.containsKey(name)) {
                throw new IllegalArgumentException("Missing required input: " + name);
            }
            Double var = inputs.get(name);
            if (var == null) throw new IllegalArgumentException("Null value for input: " + name);
            if (var < lv.getMin() || var > lv.getMax()) {
                throw new IllegalArgumentException("Input out of range for " + name + ": " + var +
                        " not in [" + lv.getMin() + "," + lv.getMax() + "]");
            }
            out.put(name, var);
        }
        return out;
    }
}

