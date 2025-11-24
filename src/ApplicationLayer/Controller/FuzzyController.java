package ApplicationLayer.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import InfrastructureLayer.Fuzzy.fuzzification.SimpleFuzzifier;
import InfrastructureLayer.Fuzzy.inference.Mamdani;
import InfrastructureLayer.Fuzzy.inference.Sugeno;
import InfrastructureLayer.Fuzzy.membership.Triangular;
import InfrastructureLayer.Fuzzy.operators.MaxOR;
import InfrastructureLayer.Fuzzy.operators.MinAND;
import InfrastructureLayer.Fuzzy.operators.MinImplication;

public class FuzzyController {

    public static void main(String[] args) {
        // Enter input Variables and output Variables
        // input Variables~
        LinguisticVariable Size = new LinguisticVariable("Size", 0, 100);
        Size.addFuzzySet(new FuzzySet("Small", new Triangular( 0, 0, 100)));
        Size.addFuzzySet(new FuzzySet("Large", new Triangular( 0, 100, 100)));

        LinguisticVariable Weight = new LinguisticVariable("Weight", 0, 100);
        Weight.addFuzzySet(new FuzzySet("Light", new Triangular( 0, 0, 100)));
        Weight.addFuzzySet(new FuzzySet("Heavy", new Triangular( 0, 100, 100)));

        // output Variables
        LinguisticVariable Quality = new LinguisticVariable("Quality", 0, 10);
        Quality.addFuzzySet(new FuzzySet("Bad", new Triangular( 0, 0, 5)));
        Quality.addFuzzySet(new FuzzySet("Medium", new Triangular( 0, 5, 10 )));
        Quality.addFuzzySet(new FuzzySet("Good", new Triangular(5, 10, 10)));
        //=====================================================================================
        // STEP1: FuzzififCation
        Map<String, Map<String, Double>> allFuzzyValues = new HashMap<>();

        SimpleFuzzifier fuzzifier1 = new SimpleFuzzifier();
        for (FuzzySet fs : Size.getFuzzySets().values()) {
            fuzzifier1.addFuzzySet(fs);
        }
        double crispInput_Size = 20;
        Map<String, Double> sizeFuzzyValues = fuzzifier1.fuzzify(crispInput_Size);
        allFuzzyValues.put("Size", sizeFuzzyValues);
         

        SimpleFuzzifier fuzzifier2 = new SimpleFuzzifier();
        for (FuzzySet fs : Weight.getFuzzySets().values()) {
            fuzzifier2.addFuzzySet(fs);
        }
        double crispInput_Weight = 25;
        Map<String, Double> weightFuzzyValues = fuzzifier2.fuzzify(crispInput_Weight);
        allFuzzyValues.put("Weight", weightFuzzyValues);
        // PRINT
        // System.out.println(allFuzzyValues);
        // System.out.println(allFuzzyValues.get("size").get("Small"));
        //=====================================================================================
        // Enter Rules  
        List<FuzzyRule>Rules = new ArrayList<>();

        // R1:  IF Size is Small AND Weight is Light THEN Quality is Bad
        FuzzyRule r1 = new FuzzyRule();
        r1.addAntecedent("Size", "Small");
        r1.addOperator("AND");
        r1.addAntecedent("Weight", "Light");
        r1.addConsequent("Quality", "Bad");
        Rules.add(r1);

        // R2: IF Size is Large AND Weight is Heavy THEN Quality is Good
        FuzzyRule r2 = new FuzzyRule();
        r2.addAntecedent("Size", "Large");
        r2.addOperator("AND");
        r2.addAntecedent("Weight", "Heavy");
        r2.addConsequent("Quality", "Good");
        Rules.add(r2);

        // R2: IF Size is Large AND Weight is Light THEN Quality is Medium
        FuzzyRule r3 = new FuzzyRule();
        r3.addAntecedent("Size", "Large");
        r3.addOperator("AND");
        r3.addAntecedent("Weight", "Light");
        r3.addConsequent("Quality", "Medium");
        Rules.add(r3);

        //=====================================================================================
        // STEP2: Inference
 
        Mamdani mamdaniEngine = new Mamdani();
        Map<String, Double> mamdaniOutputs = mamdaniEngine.evaluateRules(Rules, allFuzzyValues);
        System.out.println("Mamdani Outputs: " + mamdaniOutputs);

 
        
        //=====================================================================================
 
         
    }
}
    
