package InfrastructureLayer.Fuzzy.validation;

import DomainLayer.interfaces.Fuzzy.InputValidator;
import DomainLayer.entities.Fuzzy.LinguisticVariable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClampInputValidator implements InputValidator {
    @Override
    public Map<String, Double> validate(Map<String, Double> inputs, Map<String, LinguisticVariable> variables) {
        Map<String, Double> out = new LinkedHashMap<>();
        for (Map.Entry<String, LinguisticVariable> e : variables.entrySet()) {
            String name = e.getKey();
            LinguisticVariable lv = e.getValue();
            Double v = inputs.get(name);
            if (v == null) {
                // default: midpoint
                out.put(name, (lv.getMin() + lv.getMax()) / 2.0);
            } else {
                double clamped = Math.max(lv.getMin(), Math.min(lv.getMax(), v));
                out.put(name, clamped);
            }
        }
        return out;
    }
}

