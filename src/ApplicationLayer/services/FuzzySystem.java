package ApplicationLayer.services;

import DomainLayer.entities.Fuzzy.*;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.FuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;
import InfrastructureLayer.Fuzzy.defuzzification.Centroid;
import InfrastructureLayer.Fuzzy.fuzzification.SimpleFuzzifier;
import InfrastructureLayer.Fuzzy.inference.Mamdani;
import InfrastructureLayer.Fuzzy.membership.Triangular;

import java.util.*;

import static java.util.Collections.min;

public class FuzzySystem {
    private final Map<String, LinguisticVariable> inputVariables;
    private LinguisticVariable outputVariable;

    // Use rulebase
    private RuleBase rules;
    private InferenceStrategy inferenceStrategy;
    private DefuzzificationStrategy defuzzificationStrategy;
    private Map<String, FuzzificationStrategy> fuzzifiers;

    // Intermediate stages for debugging
    private Map<String, Map<String, Double>> fuzzifiedStage;
    private Map<FuzzyRule, Double> inferredStage;
    private Map<String, Double> aggregatedStage;
    private double crispOutput;

    public FuzzySystem() {
        this.inputVariables = new HashMap<>();
        this.rules = new RuleBase();
        this.fuzzifiers = new HashMap<>();
        this.inferenceStrategy = new Mamdani();
        this.defuzzificationStrategy = new Centroid();
    }


    public void addInputVariable(LinguisticVariable variable) {
        if(variable.getFuzzySets().isEmpty()){
            double min = variable.getMin(), max = variable.getMax(), mid = (variable.getMax()+ variable.getMin())/2.0;

            FuzzySet Low = new FuzzySet("Low",new Triangular(min,min,max));
            FuzzySet  Medium = new FuzzySet("Medium",new Triangular(min,mid,max));
            FuzzySet High = new FuzzySet("High",new Triangular(mid,max,max));
            variable.addFuzzySet(Low);
            variable.addFuzzySet(Medium);
            variable.addFuzzySet(High);
        }
        this.inputVariables.put(variable.getName(), variable);
    }

    public void setOutputVariable(LinguisticVariable variable) {
        this.outputVariable = variable;
    }

    public void addRule(FuzzyRule rule) {
        this.rules.addRule(rule);
    }

    public void saveRules(String filename) {
        this.rules.saveRulesToFile(filename);
    }

    public void loadRules(String filename) {
        this.rules.loadRulesFromFile(filename);
    }

    public void setInferenceEngine(InferenceStrategy strategy ) {
        if(strategy!=null)
        this.inferenceStrategy = strategy;
    }

    public void setDefuzzifier(DefuzzificationStrategy strategy) {
        if(strategy!=null)
        this.defuzzificationStrategy = strategy;
    }
    // Following fn need to be implemented

    ///////////////////////////////////////PiPeLine/////////////////////////////////////

    /** Step 1: Fuzzify input values */
    public Map<String, Map<String, Double>> fuzzify(Map<String, Double> crispInputs) {
        Map<String, Map<String, Double>> fuzzified = new HashMap<>();
        for (var entry : inputVariables.keySet()) {
            String varName =  entry;

            LinguisticVariable lv = inputVariables.get(varName);
            double value = crispInputs.getOrDefault(varName, 0.0);
             value = Math.min(lv.getMax(),value);
             value = Math.max(lv.getMin(),value);

                SimpleFuzzifier fuzzifier = new SimpleFuzzifier();
                for (FuzzySet fs : lv.getFuzzySets().values()) {
                    fuzzifier.addFuzzySet(fs);
                }
                fuzzified.put(varName, fuzzifier.fuzzify(value));

        }
        fuzzifiedStage = fuzzified; // store for debugging
        return fuzzified;
    }

    /** Step 2: Infer rules */
    public Map<FuzzyRule, Double> infer(Map<String, Map<String, Double>> fuzzifiedInputs) {
        Map<FuzzyRule, Double> inferredOutput = new LinkedHashMap<>();
        if (inferenceStrategy == null) return inferredOutput;

        for (FuzzyRule rule : rules.getEnabledRules()) {
            double firingStrength = inferenceStrategy.evaluateRule(rule, fuzzifiedInputs);
            inferredOutput.put(rule, firingStrength);
        }
        inferredStage = inferredOutput; // store for debugging
        return inferredOutput;
    }

    /** Step 3: Aggregate inferred outputs */
    public Map<String, Double> aggregate(Map<FuzzyRule, Double> inferredOutput) {
        Map<String, Double> aggregated = new HashMap<>();
        for (var entry : inferredOutput.entrySet()) {
            FuzzyRule rule = entry.getKey();
            double strength = entry.getValue();
            for (var outEntry : rule.getConsequents().entrySet()) {
                String outputSet = outEntry.getValue();
                aggregated.merge(outputSet, strength, Math::max); // Max aggregation
            }
        }
        aggregatedStage = aggregated; // store for debugging
        return aggregated;
    }

    /** Step 4: Defuzzify */
    public double defuzzify(Map<String, Double> aggregatedFuzzy) {
        if (defuzzificationStrategy == null) return 0.0;
        crispOutput = defuzzificationStrategy.defuzzify(aggregatedFuzzy, outputVariable);
        return crispOutput;
    }

    /** Flatten fuzzified stage for easier debugging */
    private Map<String, Double> flattenFuzzified(Map<String, Map<String, Double>> fuzzified) {
        Map<String, Double> flat = new HashMap<>();
        for (var entry : fuzzified.entrySet()) {
            String varName = entry.getKey();
            for (var setEntry : entry.getValue().entrySet()) {
                flat.put(varName + "." + setEntry.getKey(), setEntry.getValue());
            }
        }
        return flat;
    }

    /** Step 5: Full evaluation pipeline */
    public FuzzyIOPipeline evaluate(Map<String, Double> crispInputs) {
        fuzzify(crispInputs);
        infer(fuzzifiedStage);
        aggregate(inferredStage);
        defuzzify(aggregatedStage);
        return new FuzzyIOPipeline(flattenFuzzified(fuzzifiedStage), aggregatedStage, crispOutput);
    }


    /** Debug function: print all stages */
    public void printDebug() {
        System.out.println("===== FUZZIFIED STAGE =====");
        for (var entry : fuzzifiedStage.entrySet()) {
            System.out.println("Variable: " + entry.getKey());
            for (var fs : entry.getValue().entrySet()) {
                System.out.printf("  Set %s: %.4f%n", fs.getKey(), fs.getValue());
            }
        }

        System.out.println("\n===== INFERRED STAGE =====");
        for (var entry : inferredStage.entrySet()) {
            System.out.printf("Rule %d degree: %.4f%n", entry.getKey().getId(), entry.getValue());
        }

        System.out.println("\n===== AGGREGATED STAGE =====");
        for (var entry : aggregatedStage.entrySet()) {
            System.out.printf("Output Set %s: %.4f%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\n===== CRISP OUTPUT =====");
        System.out.printf("Crisp value: %.4f%n", crispOutput);
    }

    public Set<String> getInputVariableNames() {
        return inputVariables.keySet();
    }

    public Map<String, LinguisticVariable> getInputVariables() {
        return inputVariables;
    }


}
