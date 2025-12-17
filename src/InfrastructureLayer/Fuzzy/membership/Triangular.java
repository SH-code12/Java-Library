package InfrastructureLayer.Fuzzy.membership;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class Triangular implements MembershipFunction {
    private final double a, b, c;
    public Triangular(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public double membership(double x) {
        if (x <= a || x >= c){
            return (x==b)?1.0:0.0;
        }
        if (x == b) {
            return 1.0;
        }
        if (x > a && x < b) {
            return (x - a) / (b - a);
        }
        return (c - x) / (c - b);
    }

    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public double getC() {
        return c;
    }

}