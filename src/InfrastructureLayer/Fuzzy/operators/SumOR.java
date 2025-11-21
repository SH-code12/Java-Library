package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.OR_Operator;

public class SumOR implements OR_Operator {
    public double or(double a, double b){
        return Math.min(1.0, a+b);
    }
}
