package InfrastructureLayer.Fuzzy.membership;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class Trapezoidal extends MembershipFunction {

    private final double a,b,c,d;

    public Trapezoidal(String name, double a,double b,double c,double d){
        super(name);
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
    }

    @Override
    public double compute(double x){
        if (x<=a || x>=d){
            return 0.0;
        }
        if (x>=b && x<=c) {
            return 1.0;
        }
        if (x>a && x<b) {
            return (x-a)/(b-a);
        }
        return (d-x)/(d-c);
    }
}
