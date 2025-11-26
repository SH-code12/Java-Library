package PresentationLayer.Fuzzy;

import ApplicationLayer.services.FuzzySystem;
import DomainLayer.entities.Fuzzy.FuzzyIOPipeline;
import DomainLayer.entities.Fuzzy.*;
import DomainLayer.interfaces.Fuzzy.*;
import InfrastructureLayer.Fuzzy.inference.Mamdani;
import InfrastructureLayer.Fuzzy.inference.Sugeno;
import InfrastructureLayer.Fuzzy.defuzzification.*;
import InfrastructureLayer.Fuzzy.validation.*;
import InfrastructureLayer.Fuzzy.membership.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockMarketModel {


    private final FuzzySystem system;

    public StockMarketModel() {
        this.system = new FuzzySystem();
        buildStock();
    }

    private void buildStock() {
        // Price Trend input
        LinguisticVariable trend = new LinguisticVariable("PriceTrend", -10, 10);
        trend.addFuzzySet(new FuzzySet("Negative", new Trapezoidal(-15, -10, -2, 0)));
        trend.addFuzzySet(new FuzzySet("Neutral", new Triangular(-2, 0, 2)));
        trend.addFuzzySet(new FuzzySet("Positive", new Trapezoidal(0, 2, 10, 15)));
        system.addInputVariable(trend);

        // Volume input
        LinguisticVariable volume = new LinguisticVariable("Volume", 0, 1000000);
        volume.addFuzzySet(new FuzzySet("Low", new Trapezoidal(0, 0, 200000, 400000)));
        volume.addFuzzySet(new FuzzySet("Medium", new Gaussian(500000, 100000)));
        volume.addFuzzySet(new FuzzySet("High", new Trapezoidal(600000, 800000, 1000000, 1000000)));
        system.addInputVariable(volume);

        // Decision output
        LinguisticVariable decision = new LinguisticVariable("Decision", 0, 100);
        decision.addFuzzySet(new FuzzySet("Sell", new Trapezoidal(0, 0, 30, 50)));
        decision.addFuzzySet(new FuzzySet("Hold", new Triangular(40, 50, 60)));
        decision.addFuzzySet(new FuzzySet("Buy", new Trapezoidal(50, 70, 100, 100)));
        system.setOutputVariable(decision);

        // Example Rules
        FuzzyRule rule1 = new FuzzyRule(1);
        rule1.addAntecedent("PriceTrend", "Positive");
        rule1.addAntecedent("Volume", "High");
        rule1.addOperator("AND");
        rule1.addConsequent("Decision", "Buy");
        system.addRule(rule1);

        FuzzyRule rule2 = new FuzzyRule(2);
        rule2.addAntecedent("PriceTrend", "Negative");
        rule2.addAntecedent("Volume", "Low");
        rule2.addOperator("AND");
        rule2.addConsequent("Decision", "Sell");
        system.addRule(rule2);

        FuzzyRule rule3 = new FuzzyRule(3);
        rule3.addAntecedent("PriceTrend", "Neutral");
        rule3.addConsequent("Decision", "Hold");
        system.addRule(rule3);

        FuzzyRule rule4 = new FuzzyRule(4);
        rule4.addAntecedent("PriceTrend", "Positive");
        rule4.addAntecedent("Volume", "Medium");
        rule4.addOperator("AND");
        rule4.addConsequent("Decision", "Buy");
        system.addRule(rule4);


        FuzzyRule rule5 = new FuzzyRule(5);
        rule5.addAntecedent("PriceTrend", "Positive");
        rule5.addAntecedent("Volume", "Low");

        rule5.addConsequent("Decision", "Hold");
        system.addRule(rule5);

        FuzzyRule rule6 = new FuzzyRule(6);
        rule6.addAntecedent("PriceTrend", "Negative");
        rule6.addAntecedent("Volume", "High");
        rule6.addOperator("AND");
        rule6.addConsequent("Decision", "Sell");
        system.addRule(rule6);

        FuzzyRule rule7 = new FuzzyRule(7);
        rule7.addAntecedent("PriceTrend", "Negative");
        rule7.addAntecedent("Volume", "Medium");
        rule7.addOperator("AND");
        rule7.addConsequent("Decision", "Sell");
        system.addRule(rule7);

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to load rules from file? (y/n)");
        if(scanner.next().equalsIgnoreCase("y")) {
            System.out.print("Enter filename: ");
            String filename = scanner.next();
            system.loadRules(filename);
            System.out.println("Rules loaded successfully.");
        }

        chooseMembershipFn(scanner);

        Map<String, Double> inputs = new HashMap<>();

        // Enter crisp input values WITH RANGE DISPLAY
        for (String varName : system.getInputVariableNames()) {

            LinguisticVariable var = system.getInputVariables().get(varName);

            System.out.print(
                    "Enter value for " + varName +
                            " (Range: " + var.getMin() + " to " + var.getMax() + "): "
            );

            double val = scanner.nextDouble();
            inputs.put(varName, val);
        }

        // Choose input validation
        System.out.println("Select input validation strategy:\n" +
                " 1.Strict\n" +
                " 2.Clamp\n" +
                " 3.Default\n");
        int vChoice = scanner.nextInt();
        InputValidator validator = switch (vChoice) {
            case 1 -> new StrictInputValidator();
            case 2 -> new ClampInputValidator();
            default -> new DefaultInputValidator();
        };

        // Validate inputs
        Map<String, Double> validatedInputs = validator.validate(inputs, system.getInputVariables());
        System.out.println("Validated Inputs: " + validatedInputs);

        // Choose inference engine
        System.out.println("Select inference engine: \n" +
                " 1.Default/Mamdani \n" +
                " 2.Sugeno \n");
        int iChoice = scanner.nextInt();
        InferenceStrategy inference = switch (iChoice) {
            case 2 -> new Sugeno();
            default -> null;
        };
        system.setInferenceEngine(inference);

        // Choose defuzzifier
        System.out.println("Select defuzzification strategy:\n" +
                " 1. Default / Centroid\n" +
                " 2.MaxMembershipHeight\n" +
                " 3.MeanOfMaxMembership\n" +
                " 4.SugenoWeightedAverage\n");
        int dChoice = scanner.nextInt();
        DefuzzificationStrategy defuzz = switch (dChoice) {
            case 2 -> new MaxMembershipHeight();
            case 3 -> new MeanOfMaxMembership();
            case 4 -> new SugenoWeightedAverage();
            default -> null;
        };

        system.setDefuzzifier(defuzz);

        // Evaluate the fuzzy system
        FuzzyIOPipeline pipeline = system.evaluate(validatedInputs);

        // Print debug info
        System.out.println("************************ Debuging Stages ********************  \n");

        System.out.println("\nWould you like to save the current rules to a file? (y/n)");
        if(scanner.next().equalsIgnoreCase("y")) {
            System.out.print("Enter filename: ");
            String filename = scanner.next();
            system.saveRules(filename);
            System.out.println("Rules saved.");
        }

            system.printDebug();

        // Show final crisp output
       // System.out.println("\nFinal crisp output: " + pipeline.getCrispOutput());
    }

    private void chooseMembershipFn(Scanner scanner){
        System.out.println("Do you want to customize Membership Functions for any variable? (y/n)");
        if (!scanner.next().equalsIgnoreCase("y")) {
            return;
        }
        while(true){
            System.out.println("\nSelect a variable to edit (or type 'exit' to finish):");
            for (String varName : system.getInputVariableNames()) {
                System.out.println(" - " + varName);
            }
            System.out.println(" - Decision (Output)");
            String varName = scanner.next();
            if (varName.equalsIgnoreCase("exit")) break;
            LinguisticVariable selectedVar = system.getInputVariables().get(varName);
            if (selectedVar == null && system.getInputVariables().containsKey(varName) == false) {
                // Check if it is the output variable (assuming we have a getter or access to it)
                // Note: In your current FuzzySystem, you might need a getter for outputVariable.
                // For now, let's assume we are editing Inputs only, or you expose outputVariable.
                System.out.println("Variable not found (or is output variable without getter).");
                continue;
            }
            System.out.println("Select a Fuzzy Set to modify:");
            Map<String, FuzzySet> sets = selectedVar.getFuzzySets();
            for (String setName : sets.keySet()) {
                System.out.println(" - " + setName);
            }
            String setName = scanner.next();
            FuzzySet selectedSet = sets.get(setName);

            if (selectedSet == null) {
                System.out.println("Fuzzy set not found.");
                continue;
            }
            System.out.println("Current MF: " + selectedSet.getName());
            System.out.println("Select new shape:\n 1. Triangular\n 2. Trapezoidal\n 3. Gaussian");
            int shapeChoice = scanner.nextInt();

            try {
                MembershipFunction newMF = createMembershipFunction(shapeChoice, scanner);
                // Update the set (assuming FuzzySet has a setter, otherwise we replace the object)
                selectedSet.setMembershipFunction(newMF);
                System.out.println("Membership function updated successfully!");
            } catch (Exception e) {
                System.out.println("Error creating MF: " + e.getMessage());
            }

        }
    }
    private MembershipFunction createMembershipFunction(int choice, Scanner scanner) {
        return switch (choice) {
            case 1 -> {
                System.out.print("Enter a (left), b (peak), c (right): ");
                yield new Triangular(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
            }
            case 2 -> {
                System.out.print("Enter a (left), b (left-peak), c (right-peak), d (right): ");
                yield new Trapezoidal(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
            }
            case 3 -> {
                System.out.print("Enter mean (center) and sigma (width): ");
                yield new Gaussian(scanner.nextDouble(), scanner.nextDouble());
            }
            default -> throw new IllegalArgumentException("Invalid shape choice");
        };
    }
}
