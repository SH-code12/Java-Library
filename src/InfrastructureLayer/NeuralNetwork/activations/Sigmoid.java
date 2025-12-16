package InfrastructureLayer.NeuralNetwork.activations;

import DomainLayer.interfaces.NeuralNetwork.Activation;

public class Sigmoid implements Activation {

    @Override
    public double[][] activate(double[][] x) {
        int m=x.length, n=x[0].length;
        double[][] out = new double[m][n];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j] = 1.0 / (1.0 + Math.exp(-x[i][j]));
            }
        }
        return out;
    }
    @Override
    public double[][] derivative(double[][] x) {
        double[][] s = activate(x);
        int m=s.length,n=s[0].length;
        double[][] out = new double[m][n];
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j] = s[i][j] * (1 - s[i][j]);
            }
        }
        return out;
    }
}
