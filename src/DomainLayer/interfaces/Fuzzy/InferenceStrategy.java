package DomainLayer.interfaces.Fuzzy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import DomainLayer.entities.Fuzzy.FuzzyRule;

public interface InferenceStrategy {

    // evaluate single rule based on which call it Mamdani or Sugeno
    double evaluateRule(FuzzyRule rule, Map<String, Map<String, Double>> fuzzifiedInputs);

    // evaluate list of rules and return activations
    default Map<String, Double> evaluateRules(List<FuzzyRule> rules, Map<String, Map<String, Double>> fuzzifiedInputs) {
        Map<String, Double> activations = new LinkedHashMap<>();
        for (int i = 0; i < rules.size(); i++) {
            FuzzyRule rule = rules.get(i);
            double value = evaluateRule(rule, fuzzifiedInputs); // based on which call it Mamdani or Sugeno
            // Get the first entry (if you only have one)
            String ConsequentVariable = rule.getConsequents().keySet().iterator().next();
            String ConsequentName = rule.getConsequents().get(ConsequentVariable);
            // final Result Map :)
            activations.put(ConsequentName, value);
        }
        return activations;
    }
}