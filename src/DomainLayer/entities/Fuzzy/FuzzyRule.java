package DomainLayer.entities.Fuzzy;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FuzzyRule {
    int id ;
    private double weight = 1.0;
    private boolean isEnabled = true;
     
    private final Map<String, String> antecedents = new LinkedHashMap<>();
    private final List<String> operators = new ArrayList<>();
    private final Map<String, String> Consequents = new LinkedHashMap<>();
    private final Map<String,Double> CrispConsequents = new LinkedHashMap<>();



    public FuzzyRule() {
    }
    public FuzzyRule(int id) {

        this.id = id;
    }

    // Antecedent List and its inside Operators List 
    public void addAntecedent(String variableName, String fuzzySetName) {

        antecedents.put(variableName, fuzzySetName);
    }
    public Map<String, String> getAntecedents() {
        return antecedents;
    }

    public void addOperator(String OperatorName) {
        operators.add(OperatorName);
    }
    public List<String> getOperators() {
        int requiredOperators = antecedents.size() - 1;

        if (requiredOperators > 0) {
            while (operators.size() < requiredOperators) {
                operators.add("AND"); // Sensible Default
            }
        }
        return operators;
    }

    // Consequents for "Mamdani"
    public void addConsequent(String variableName, String fuzzySetName) {
        Consequents.put(variableName, fuzzySetName);
    }
    public Map<String, String> getConsequents() {
        return Consequents;
    }
    
    // Consequents for "Sugeno"
    public void addCrispConsequent(String variableName, double value) {
        CrispConsequents.put(variableName, value);
    }
    public Map<String, Double> getCrispConsequents() {
        return CrispConsequents;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public int getId() {
        return id;
    }
}