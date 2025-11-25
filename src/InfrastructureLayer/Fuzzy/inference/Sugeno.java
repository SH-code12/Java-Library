package InfrastructureLayer.Fuzzy.inference;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;
import DomainLayer.interfaces.Fuzzy.AND_Operator;
import DomainLayer.interfaces.Fuzzy.OR_Operator;
import InfrastructureLayer.Fuzzy.operators.MaxOR;
import InfrastructureLayer.Fuzzy.operators.MinAND;

import java.util.Map;

public class Sugeno implements InferenceStrategy {

    private final AND_Operator andOperator = new MinAND();
    private final OR_Operator orOperator = new MaxOR();

    public Sugeno(){}

    @Override
    public double evaluateRule(FuzzyRule rule, Map<String, Map<String, Double>> fuzzifiedInputs) {
        return 0;
        
    }
}
