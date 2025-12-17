package InfrastructureLayer.NeuralNetwork.activations;

import DomainLayer.interfaces.NeuralNetwork.Activation;

public class ReLU implements Activation {

    @Override
    public double[][] activate(double[][] x) {
        int m=x.length,n=x[0].length;
        double[][] out=new double[m][n];

        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j]=Math.max(0, x[i][j]);
            }
        }
        return out;
    }
    @Override
    public double[][] derivative(double[][] x) {
        int m=x.length,n=x[0].length;
        double[][] out=new double[m][n];

        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                out[i][j]= x[i][j]>0 ? 1.0 : 0.0;
            }
        }
        return out;
    }
}
