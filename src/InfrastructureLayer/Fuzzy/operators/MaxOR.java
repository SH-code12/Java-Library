package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.OR_Operator;

public class MaxOR implements OR_Operator {
    public double or(double a, double b){
        return Math.max(a,b);
    }
}
