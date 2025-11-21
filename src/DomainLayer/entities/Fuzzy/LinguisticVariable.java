package DomainLayer.entities.Fuzzy;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinguisticVariable {
    private final String name;
    private final double min, max;
    private final Map<String, FuzzySet> fuzzySets = new LinkedHashMap<>();
    public LinguisticVariable(String name,double min,double max){
        this.name=name;
        this.min=min;
        this.max=max;}
    public String getName(){
        return name;}
    public double getMin(){
        return min;}
    public double getMax(){
        return max;}
    public void addFuzzySet(FuzzySet s){
        fuzzySets.put(s.getName(), s);}
    public Map<String,FuzzySet> getFuzzySets(){
        return fuzzySets;}
}