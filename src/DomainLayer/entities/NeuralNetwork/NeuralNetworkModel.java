package DomainLayer.entities.NeuralNetwork;

import DomainLayer.interfaces.NeuralNetwork.Layer;
//import InfrastructureLayer.NeuralNetwork.layers.DenseLayer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkModel {
    List<Layer> layers;
    public NeuralNetworkModel() {
        this.layers = new ArrayList<>();
    }


    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public List<Layer> getLayers() {
        return layers;
    }
}
