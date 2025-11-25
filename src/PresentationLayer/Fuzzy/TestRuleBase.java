package PresentationLayer.Fuzzy;

import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.entities.Fuzzy.RuleBase;

import java.util.Map;

public class TestRuleBase {
    public static void main(String[] args) {
        System.out.println("=== 1. Testing Rule Creation ===");

        // Scenario: IF Temperature is High AND Humidity is Low THEN FanSpeed is Fast
        FuzzyRule rule1 = new FuzzyRule(1);
        rule1.addAntecedent("Temperature", "High");
        rule1.addOperator("AND");
        rule1.addAntecedent("Humidity", "Low");
        rule1.addConsequent("FanSpeed", "Fast");

        // FIX: Print the ID, not the operator list

        System.out.println("Rule 1 Created: ID " + rule1.getId());

        // FIX: Logic check
        System.out.println("Antecedents count: " + rule1.getAntecedents().size());

        // Scenario: Sugeno Rule
        FuzzyRule rule2 = new FuzzyRule(2);
        rule2.addAntecedent("Traffic", "Heavy");
        rule2.addCrispConsequent("LightTime", 10);
        rule2.setWeight(0.8);

        System.out.println("Rule 2 Created with Weight: " + rule2.getWeight());

        System.out.println("\n=== 2. Testing RuleBase API (Manager) ===");
        RuleBase ruleBase = new RuleBase();

        // Add Rules
        ruleBase.addRule(rule1);
        ruleBase.addRule(rule2);

        // FIX: Matches your naming convention .getRules()
        System.out.println("Total Rules: " + ruleBase.getRules().size());

        // FIX: Matches your naming convention .getEnabledRules()
        System.out.println("Active Rules before disable: " + ruleBase.getEnabledRules().size());

        // Test Disable
        System.out.println("-> Disabling Rule 1...");

        // FIX: Pass int ID directly. 'FuzzyOperator' is for logic (AND/OR), not IDs.
        ruleBase.setRuleStatus(1, false);

        System.out.println("Active Rules after disable: " + ruleBase.getEnabledRules().size());

        // Test Edit (Update)
        System.out.println("-> Updating Rule 2...");
        FuzzyRule newRule2 = new FuzzyRule(2);
        newRule2.addAntecedent("Traffic", "Medium");
        newRule2.addCrispConsequent("LightTime", 30);

        // FIX: Pass int ID directly
        ruleBase.updateRule(2, newRule2);

        // Verify Update
        FuzzyRule updated = ruleBase.getRules().get(1); // Get the second rule

        // FIX: Maps are accessed by KEY ("Traffic"), not by Index or Optional
        String val = updated.getAntecedents().get("Traffic");
        System.out.println("Rule 2 Variable is now: " + val);

        System.out.println("\n=== 3. Testing Persistence (Save/Load) ===");

        // 1. Save the current rules (Rule 1 disabled, Rule 2 updated)
        ruleBase.saveRulesToFile("rules_test.txt");

        // 2. Create a fresh RuleBase and load them back
        RuleBase loadedBase = new RuleBase();
        loadedBase.loadRulesFromFile("rules_test.txt");

        // 3. Verify
        System.out.println("Loaded Rules Count: " + loadedBase.getRules().size()); // Should be 2

        FuzzyRule loadedRule2 = loadedBase.getRules().get(1);
        System.out.println("Loaded Rule 2 Weight: " + loadedRule2.getWeight()); // Should be 0.8
        System.out.println("Loaded Rule 2 Output: " + loadedRule2.getCrispConsequents().get("LightTime")); // Should be 30.0
    }
}