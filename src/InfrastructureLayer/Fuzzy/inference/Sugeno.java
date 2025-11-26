package InfrastructureLayer.Fuzzy.inference;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;
import DomainLayer.interfaces.Fuzzy.AND_Operator;
import DomainLayer.interfaces.Fuzzy.OR_Operator;
import InfrastructureLayer.Fuzzy.operators.MaxOR;
import InfrastructureLayer.Fuzzy.operators.MinAND;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sugeno implements InferenceStrategy {

    private final AND_Operator andOperator = new MinAND();
    private final OR_Operator orOperator = new MaxOR();

    public Sugeno(){}

    //===========================================================================
    @Override
    public double evaluateRule(FuzzyRule rule, Map<String, Map<String, Double>> fuzzifiedInputs) {
        double Output = 0.0;
        var antecedents = rule.getAntecedents();
        var operators = rule.getOperators();

        int idx = 0;
        for (var entry : antecedents.entrySet()) {
            String variable = entry.getKey();
            String fuzzySet = entry.getValue();

            double value = fuzzifiedInputs.get(variable).get(fuzzySet);

            if (idx == 0) Output = value;
            else {
                String op = operators.get(idx - 1);
                if (op.equalsIgnoreCase("AND")) Output = andOperator.and(Output, value);
                else if (op.equalsIgnoreCase("OR")) Output = orOperator.or(Output, value);
            }
            idx++;
        }
        return Output;
        
    }
    //===========================================================================
    public List<Double> evaluateRules(List<FuzzyRule> rules, Map<String, Map<String, Double>> fuzzifiedInputs) {
        List <Double> activations = new ArrayList();
        for (int i = 0; i < rules.size(); i++) {
            FuzzyRule rule = rules.get(i);
            double value = evaluateRule(rule, fuzzifiedInputs); // based on which call it Mamdani or Sugeno
            // Get the first entry (if you only have one)
            activations.add(value);
        }
        return activations;
    }
    //===========================================================================
}