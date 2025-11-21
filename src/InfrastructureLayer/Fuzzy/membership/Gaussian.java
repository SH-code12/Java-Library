package InfrastructureLayer.Fuzzy.membership;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class Gaussian implements MembershipFunction {
    private final double mean, sigma;
    public Gaussian(double mean,double sigma){this.mean=mean;this.sigma=sigma;}
    @Override
    public double membership(double x){
        double expo = -Math.pow(x-mean,2)/(2*sigma*sigma);
        return Math.exp(expo);
    }
}
