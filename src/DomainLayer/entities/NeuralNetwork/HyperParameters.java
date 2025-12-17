package DomainLayer.entities.NeuralNetwork;

import DomainLayer.interfaces.NeuralNetwork.LossFunction;
import DomainLayer.interfaces.NeuralNetwork.Optimizer;
import DomainLayer.interfaces.NeuralNetwork.WeightInitializer;

import java.util.List;
import java.util.Scanner;

public class HyperParameters {

    public int epochs;
    public int batchSize;
    public double learningRate;

    public Optimizer optimizer;
    public LossFunction lossFunction;

    public List<LayerConfig> layerConfigs;

    public HyperParameters(List<LayerConfig> layerConfigs,
                           Optimizer optimizer,
                           LossFunction lossFunction,
                           int epochs,
                           int batchSize,
                           double learningRate) {
        this.layerConfigs = layerConfigs;
        this.optimizer = optimizer;
        this.lossFunction = lossFunction;
        this.epochs = epochs;
        this.batchSize = batchSize;
        this.learningRate = learningRate;


    }
}
