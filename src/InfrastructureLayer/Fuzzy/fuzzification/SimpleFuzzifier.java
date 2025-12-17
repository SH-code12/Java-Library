package InfrastructureLayer.Fuzzy.fuzzification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.interfaces.Fuzzy.FuzzificationStrategy;
import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class SimpleFuzzifier implements FuzzificationStrategy{
    private List<FuzzySet> fs;

    public SimpleFuzzifier() {
        fs = new ArrayList<>();
    }

    public void addFuzzySet(FuzzySet f) {
        fs.add(f);
    }

    public Map<String, Double> fuzzify(double crispValue) {
        Map<String, Double> fuzzyValues = new HashMap<>();
        for (FuzzySet f : fs) {
            fuzzyValues.put(f.getName(),f.getMf().membership(crispValue));
        }
        return fuzzyValues;
    }
}
