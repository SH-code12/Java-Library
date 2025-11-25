package ApplicationLayer.services;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.entities.Fuzzy.RuleBase;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzySystem {
    private Map<String,LinguisticVariable> Inputvariables;
    private LinguisticVariable Outputvariables;
    private List<FuzzyRule> rules;
    private InferenceStrategy inferenceStrategy;
    private DefuzzificationStrategy defuzzificationStrategy;


    public FuzzySystem() {
        this.Inputvariables = new HashMap<>();
        this.rules = new ArrayList<>();
    }


    public void addInputVariable(LinguisticVariable variable) {
        this.Inputvariables.put(variable.getName(), variable);
    }
    public void setOutputVariable(LinguisticVariable variable) {
        this.Outputvariables = variable;
    }
    public void addRule(FuzzyRule rule) {
        this.rules.add(rule);
    }

    public void setInferenceEngine(InferenceStrategy strategy) {
        this.inferenceStrategy = strategy;
    }

    public void setDefuzzifier(DefuzzificationStrategy strategy) {
        this.defuzzificationStrategy = strategy;
    }
    // Following fn need to be implemented
    public double evaluate(Map<String, Double> crispInputs){}

}
