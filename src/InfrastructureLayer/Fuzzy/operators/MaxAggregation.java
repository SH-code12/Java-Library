package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.AggregationOperator;

public class MaxAggregation implements AggregationOperator {
    public double aggregate(double a, double b){
        return Math.max(a,b);
    }
}
