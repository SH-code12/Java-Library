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
        FuzzyRule rule1 = new FuzzyRule();
        rule1.addAntecedent("PriceTrend", "Positive");
        rule1.addAntecedent("Volume", "High");
        rule1.addOperator("AND");
        rule1.addConsequent("Decision", "Buy");
        system.addRule(rule1);

        FuzzyRule rule2 = new FuzzyRule();
        rule2.addAntecedent("PriceTrend", "Negative");
        rule2.addAntecedent("Volume", "Low");
        rule2.addOperator("OR");
        rule2.addConsequent("Decision", "Sell");
        system.addRule(rule2);

        FuzzyRule rule3 = new FuzzyRule();
        rule3.addAntecedent("PriceTrend", "Neutral");
        rule3.addConsequent("Decision", "Hold");
        system.addRule(rule3);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
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
                " 1.Mamdani \n" +
                " 2.Sugeno \n");
        int iChoice = scanner.nextInt();
        InferenceStrategy inference = switch (iChoice) {
            case 2 -> new Sugeno();
            default -> new Mamdani();
        };
        system.setInferenceEngine(inference);

        // Choose defuzzifier
        System.out.println("Select defuzzification strategy:\n" +
                " 1.Centroid\n" +
                " 2.MaxMembershipHeight\n" +
                " 3.MeanOfMaxMembership\n" +
                " 4.SugenoWeightedAverage\n");
        int dChoice = scanner.nextInt();
        DefuzzificationStrategy defuzz = switch (dChoice) {
            case 2 -> new MaxMembershipHeight();
            case 3 -> new MeanOfMaxMembership();
            case 4 -> new SugenoWeightedAverage();
            default -> new Centroid();
        };
        system.setDefuzzifier(defuzz);

        // Evaluate the fuzzy system
        FuzzyIOPipeline pipeline = system.evaluate(validatedInputs);

        // Print debug info
        System.out.println("************************ Debuging Stages ********************  \n");

        system.printDebug();

        // Show final crisp output
        System.out.println("\nFinal crisp output: " + pipeline.getCrispOutput());
    }
}
