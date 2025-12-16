package InfrastructureLayer.NeuralNetwork.layers;

import DomainLayer.interfaces.NeuralNetwork.Activation;
import DomainLayer.interfaces.NeuralNetwork.Layer;
import DomainLayer.interfaces.NeuralNetwork.Optimizer;
import InfrastructureLayer.NeuralNetwork.util.Matrix;

public class DenseLayer implements Layer {

    private Activation activationfn;

    private double [] lastInput;


    private double[] lastOutput;
    private double[][] weights;
    private double[] biases;
    private Optimizer optimizer;

    public DenseLayer(int insize, int outsize,Optimizer o) {
       weights = new double[insize][outsize];
       for(int i =0;i<insize;i++){
           for(int j =0;j<outsize;j++)
               weights[i][j] = Math.random();
       }
       this.optimizer = o;
    }

    @Override
    public void forward() {

    }

    @Override
    public double [] backward(double[] errorMatrixOfnext, double lr) {
     double [] grad = activationfn.deravative(lastOutput);
     double[] delta = Matrix.multiply(errorMatrixOfnext , grad) ;
     // error for previous layer
        double []prevError = new double [lastInput.length];
        for(int i =0;i<this.lastInput.length;i++){
            for(int j =0;j<delta.length;j++){
                prevError[i]+= this.weights[i][j] * delta[j];
            }
        }


        // update weights
     for(int i =0;i<this.lastInput.length;i++){
         for(int j =0;j<delta.length;j++) {
             double g = this.lastInput[i] * delta[j];
             weights[i][j] = optimizer.update(weights[i][j],g,lr);
         }

     }


     // update biases
        for(int j =0;j<delta.length;j++){
            biases[j] = biases[j]-(lr*delta[j]);
        }

     return prevError;


    }
}
