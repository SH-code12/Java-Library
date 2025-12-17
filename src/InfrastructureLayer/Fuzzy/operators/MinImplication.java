package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.ImplicationOperator;

public class MinImplication implements ImplicationOperator {
    public double imply(double a, double b){
        return Math.min(a,b);
    }
}
