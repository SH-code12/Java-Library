package InfrastructureLayer.Fuzzy.fuzzification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class SimpleFuzzifier {
    private List<MembershipFunction> mfs;

    public SimpleFuzzifier() {
        mfs = new ArrayList<>();
    }

    public void addMembershipFunction(MembershipFunction mf) {
        mfs.add(mf);
    }

    public Map<String, Double> fuzzify(double crispValue) {
        Map<String, Double> fuzzyValues = new HashMap<>();
        for (MembershipFunction mf : mfs) {
            fuzzyValues.put(mf.getName(), mf.compute(crispValue));
        }
        return fuzzyValues;
    }
}

// How To Run In Main:-

/*  
SimpleFuzzifier fuzzifier = new SimpleFuzzifier();
fuzzifier.addMembershipFunction(new TriangularMF("Cold", 0, 0, 20));
fuzzifier.addMembershipFunction(new TriangularMF("Warm", 15, 25, 35));
fuzzifier.addMembershipFunction(new TriangularMF("Hot", 30, 40, 50));

double temperature = 30;
Map<String, Double> fuzzy = fuzzifier.fuzzify(temperature);

System.out.println(fuzzy); // {Cold=0.0, Warm=0.5, Hot=0.3333...}

*/