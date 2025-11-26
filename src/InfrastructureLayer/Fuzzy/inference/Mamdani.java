package InfrastructureLayer.Fuzzy.inference;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;
import DomainLayer.interfaces.Fuzzy.AND_Operator;
import DomainLayer.interfaces.Fuzzy.OR_Operator;
import InfrastructureLayer.Fuzzy.operators.MaxOR;
import InfrastructureLayer.Fuzzy.operators.MinAND;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mamdani implements InferenceStrategy {

    private   AND_Operator andOperator = new MinAND();
    private   OR_Operator orOperator = new MaxOR();

    public Mamdani(){}

    //===========================================================================
    public Map<String, Double> evaluateRules(List<FuzzyRule> rules, Map<String, Map<String, Double>> fuzzifiedInputs) {
        Map<String, Double> activations = new LinkedHashMap<>();
        for (int i = 0; i < rules.size(); i++) {
            FuzzyRule rule = rules.get(i);
            double value = evaluateRule(rule, fuzzifiedInputs); // based on which call it Mamdani or Sugeno
    
            String ConsequentVariable = rule.getConsequents().keySet().iterator().next();
            String ConsequentName = rule.getConsequents().get(ConsequentVariable);
            // final Result Map :)
            activations.put(ConsequentName, value);
             
        }
        return activations;
    }
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
    
}