package ApplicationLayer.services;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;

import java.util.List;

public class FuzzySystem {
    List<String> Inputvariables;
    List<String> Outputvariables;
    List<FuzzyRule> rules;
    InferenceStrategy inferenceStrategy;
    DefuzzificationStrategy defuzzificationStrategy;
    public void setInferenceEngine(InferenceStrategy type){}
    public void setDefuzzifier(DefuzzificationStrategy type){}
    public void addInputVariable(){}

    public void createRule(){}
}
