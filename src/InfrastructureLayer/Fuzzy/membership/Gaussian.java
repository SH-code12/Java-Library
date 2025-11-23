package InfrastructureLayer.Fuzzy.membership;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class Gaussian extends MembershipFunction {
    private final double mean, sigma;

    public Gaussian(String name, double mean,double sigma){
        super(name);
        this.mean=mean;
        this.sigma=sigma;
    }
    @Override
    public double compute(double x){

        double expo = -Math.pow(x-mean,2)/(2*sigma*sigma);

        return Math.exp(expo);
    }
}
