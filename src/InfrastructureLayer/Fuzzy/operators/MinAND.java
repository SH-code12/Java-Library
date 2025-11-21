package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.AND_Operator;

public class MinAND implements AND_Operator {
    public double and(double a, double b){
        return Math.min(a,b);
    }
}
