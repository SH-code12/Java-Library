package InfrastructureLayer.Fuzzy.operators;

import DomainLayer.interfaces.Fuzzy.ImplicationOperator;

public class ProductImplication implements ImplicationOperator {
    public double imply(double a, double b){
        return a*b;
    }
}
