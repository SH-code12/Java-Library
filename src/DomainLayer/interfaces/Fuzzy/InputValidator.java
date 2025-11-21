package DomainLayer.interfaces.Fuzzy;

import DomainLayer.entities.Fuzzy.LinguisticVariable;

import java.util.Map;

public interface InputValidator {

    Map<String, Double> validate(Map<String, Double> inputs, Map<String, LinguisticVariable> variables);
}
