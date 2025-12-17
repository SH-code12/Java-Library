package DomainLayer.entities.NeuralNetwork;

import DomainLayer.interfaces.NeuralNetwork.Activation;
import DomainLayer.interfaces.NeuralNetwork.Optimizer;
import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;

public class LayerConfig {
    public int inputSize;
    public int outputSize;
    public Activation activation;
    public WeightInitializer initializer;
    public Optimizer optimizer;

    public LayerConfig(int in, int out, Activation act, WeightInitializer init,Optimizer opt) {
        inputSize = in;
        outputSize = out;
        activation = act;
        initializer = init;
        optimizer = opt;
    }
}
