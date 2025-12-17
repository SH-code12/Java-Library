package DomainLayer.entities.Fuzzy;

import java.util.Map;

public class FuzzyIOPipeline {
    public Map<String, Double> fuzzified;

    public Map<String, Double> aggregated;
    public double crispOutput;

    public FuzzyIOPipeline(Map<String, Double> fuzzy, Map<String,Double> aggregate, double crips){
        this.fuzzified = fuzzy;
        this.aggregated = aggregate;
        this.crispOutput = crips;
    }

    public Map<String, Double> getFuzzified() {
        return fuzzified;
    }

    public Map<String, Double> getAggregated() {
        return aggregated;
    }

    public double getCrispOutput() {
        return crispOutput;
    }
}

