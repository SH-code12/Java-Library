package DomainLayer.interfaces.Fuzzy;
import java.util.Map;
import DomainLayer.entities.Fuzzy.FuzzyRule;

public interface InferenceStrategy {
    // evaluate single rule 
    double evaluateRule(FuzzyRule rule, Map<String, Map<String, Double>> fuzzifiedInputs);
}