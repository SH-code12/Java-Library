package ApplicationLayer.services;

import DomainLayer.entities.Fuzzy.*;
import DomainLayer.interfaces.Fuzzy.DefuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.FuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.InferenceStrategy;
import InfrastructureLayer.Fuzzy.fuzzification.SimpleFuzzifier;

import java.util.*;

public class FuzzySystem {
    private final Map<String, LinguisticVariable> inputVariables;
    private LinguisticVariable outputVariable;
    private List<FuzzyRule> rules;
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
        this.rules = new java.util.ArrayList<>();
        this.fuzzifiers = new HashMap<>();
    }


    public void addInputVariable(LinguisticVariable variable) {
        this.inputVariables.put(variable.getName(), variable);
    }

    public void setOutputVariable(LinguisticVariable variable) {
        this.outputVariable = variable;
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

    ///////////////////////////////////////PiPeLine/////////////////////////////////////

    /** Step 1: Fuzzify input values */
    public Map<String, Map<String, Double>> fuzzify(Map<String, Double> crispInputs) {
        Map<String, Map<String, Double>> fuzzified = new HashMap<>();
        for (var entry : crispInputs.entrySet()) {
            String varName = entry.getKey();
            double value = entry.getValue();
            LinguisticVariable lv = inputVariables.get(varName);
            if (lv != null) {
                SimpleFuzzifier fuzzifier = new SimpleFuzzifier();
                for (FuzzySet fs : lv.getFuzzySets().values()) {
                    fuzzifier.addFuzzySet(fs);
                }
                fuzzified.put(varName, fuzzifier.fuzzify(value));
            }
        }
        fuzzifiedStage = fuzzified; // store for debugging
        return fuzzified;
    }

    /** Step 2: Infer rules */
    public Map<FuzzyRule, Double> infer(Map<String, Map<String, Double>> fuzzifiedInputs) {
        Map<FuzzyRule, Double> inferredOutput = new LinkedHashMap<>();
        if (inferenceStrategy == null) return inferredOutput;

        for (FuzzyRule rule : rules) {
            if (!rule.isEnabled()) continue;
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
