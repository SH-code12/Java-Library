package InfrastructureLayer.NeuralNetwork.optimizers;

import DomainLayer.interfaces.NeuralNetwork.Optimizer;

public class Adam implements Optimizer {

    private final double beta1=0.9, beta2=0.999, eps=1e-8;
    private double t = 0;
    private double[][] mW = null, vW = null;
    private double[] mb = null, vb = null;

    @Override
    public void applyUpdate(double[][] W, double[][] dW, double[] b, double[] db, double learningRate) {
        if(mW==null){
            mW=new double[W.length][W[0].length];
            vW=new double[W.length][W[0].length];
            mb=new double[b.length]; vb=new double[b.length];
        }
        t++;
        for(int i=0;i<W.length;i++){
            for(int j=0;j<W[0].length;j++){
                mW[i][j] = beta1 * mW[i][j] + (1-beta1) * dW[i][j];
                vW[i][j] = beta2 * vW[i][j] + (1-beta2) * dW[i][j] * dW[i][j];
                double mHat = mW[i][j] / (1 - Math.pow(beta1, t));
                double vHat = vW[i][j] / (1 - Math.pow(beta2, t));
                W[i][j] -= learningRate * mHat / (Math.sqrt(vHat) + eps);
            }
        }
        for(int j=0;j<b.length;j++){
            mb[j] = beta1*mb[j] + (1-beta1)*db[j];
            vb[j] = beta2*vb[j] + (1-beta2)*db[j]*db[j];
            double mHat = mb[j]/(1-Math.pow(beta1,t));
            double vHat = vb[j]/(1-Math.pow(beta2,t));
            b[j] -= learningRate * mHat / (Math.sqrt(vHat) + eps);
        }
    }
}
