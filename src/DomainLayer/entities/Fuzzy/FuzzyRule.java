package DomainLayer.entities.Fuzzy;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FuzzyRule {
     
    private final Map<String, String> antecedents = new LinkedHashMap<>();
    private final List<String> operators = new ArrayList<>();
    private final Map<String, String> Consequents = new LinkedHashMap<>();
    private final Map<String, Integer> CrispConsequents = new LinkedHashMap<>();
    
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
    public void addCrispConsequent(String variableName, int value) {
        CrispConsequents.put(variableName, value);
    }
    public Map<String, Integer> getCrispConsequents() {
        return CrispConsequents;
    }
     
}
