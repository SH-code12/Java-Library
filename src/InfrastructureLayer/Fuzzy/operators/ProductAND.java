package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.AND_Operator;

public class ProductAND implements AND_Operator {
    public double and(double a, double b){
        return a*b;
    }
}
