package DomainLayer.entities.Fuzzy;

import java.util.Map;

public class FuzzyIOPipeline {
    public Map<String, Map<String, Double>> fuzzified;

    public Map<String, Map<String, Double>> inferred;
    public double crispOutput;

    public FuzzyIOPipeline(Map<String, Map<String, Double>> fuzzy, Map<String, Map<String, Double>> inferr, double crips){
        this.fuzzified = fuzzy;
        this.inferred = inferr;
        this.crispOutput = crips;
    }
}

