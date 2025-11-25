package DomainLayer.interfaces.Fuzzy;

import DomainLayer.entities.Fuzzy.LinguisticVariable;

import java.util.Map;

public interface DefuzzificationStrategy {

    double defuzzify(Map<String,Double> inferred, LinguisticVariable outputVariable);
}
