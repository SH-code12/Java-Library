package DomainLayer.interfaces.Fuzzy;

import java.util.Map;

public interface FuzzificationStrategy {
    public Map<String, Double> fuzzify(double crispValue);
}
