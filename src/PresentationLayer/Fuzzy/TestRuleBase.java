package PresentationLayer.Fuzzy;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.entities.Fuzzy.RuleBase;
import InfrastructureLayer.Fuzzy.rulebase.RuleBaseEditor;

import java.io.File;
import java.util.Map;

public class TestRuleBase {
    public static void main(String[] args) {

        System.out.println("=== 1. Testing Rule Creation ===");

        // Rule 1: IF Temperature is High AND Humidity is Low THEN FanSpeed is Fast
        FuzzyRule rule1 = new FuzzyRule(1);
        rule1.addAntecedent("Temperature", "High");
        rule1.addOperator("AND");
        rule1.addAntecedent("Humidity", "Low");
        rule1.addConsequent("FanSpeed", "Fast");

        System.out.println("Rule 1 Created: ID " + rule1.getId());
        System.out.println("Antecedents count: " + rule1.getAntecedents().size());

        // Rule 2: Sugeno-style rule
        FuzzyRule rule2 = new FuzzyRule(2);
        rule2.addAntecedent("Traffic", "Heavy");
        rule2.addCrispConsequent("LightTime", 10);
        rule2.setWeight(0.8);

        System.out.println("Rule 2 Created with Weight: " + rule2.getWeight());

        System.out.println("\n=== 2. Testing RuleBaseEditor API ===");

        RuleBase ruleBase = new RuleBase();
        RuleBaseEditor editor = new RuleBaseEditor(ruleBase);

        // Add rules
        editor.addRule(rule1);
        editor.addRule(rule2);

        System.out.println("Total Rules: " + ruleBase.getRules().size());
        System.out.println("Active Rules before disable: " + ruleBase.getEnabledRules().size());

        // Disable Rule 1
        System.out.println("-> Disabling Rule 1...");
        editor.enableRule(1, false);
        System.out.println("Active Rules after disable: " + ruleBase.getEnabledRules().size());

        // Update Rule 2 but preserve its weight
        System.out.println("-> Updating Rule 2...");
        FuzzyRule updatedRule2 = new FuzzyRule(2);
        updatedRule2.addAntecedent("Traffic", "Medium");
        updatedRule2.addCrispConsequent("LightTime", 30);
        updatedRule2.setWeight(ruleBase.findRulebyId(2).getWeight()); // Preserve weight

        editor.updateRule(2, updatedRule2);

        // Verify update
        FuzzyRule checkRule2 = ruleBase.findRulebyId(2);
        System.out.println("Rule 2 Variable is now: " + checkRule2.getAntecedents().get("Traffic"));
        System.out.println("Rule 2 Weight is now: " + checkRule2.getWeight());

        // Set weight for Rule 1
        editor.setRuleWeight(1, 0.75);
        System.out.println("Rule 1 weight: " + ruleBase.findRulebyId(1).getWeight());

        System.out.println("\n=== 3. Testing Persistence (Save/Load) ===");

        File f = new File("rules_test.txt");
        System.out.println("File exists before save: " + f.exists() + ", size: " + f.length());

        // Save current rules
        editor.saveRulesToFile("rules_test.txt");
        System.out.println("File saved. Size after save: " + f.length());

        // Create fresh RuleBase and editor
        RuleBase loadedBase = new RuleBase();
        RuleBaseEditor loadedEditor = new RuleBaseEditor(loadedBase);
        loadedEditor.loadRulesFromFile("rules_test.txt");

        // Verify loaded rules
        System.out.println("Loaded Rules Count: " + loadedBase.getRules().size());

        FuzzyRule loadedRule1 = loadedBase.findRulebyId(1);
        FuzzyRule loadedRule2 = loadedBase.findRulebyId(2);

        System.out.println("Loaded Rule 1 Enabled: " + loadedRule1.isEnabled() + ", Weight: " + loadedRule1.getWeight());
        System.out.println("Loaded Rule 2 Enabled: " + loadedRule2.isEnabled() + ", Weight: " + loadedRule2.getWeight());
        System.out.println("Loaded Rule 2 Output: " + loadedRule2.getCrispConsequents().get("LightTime"));
    }
}