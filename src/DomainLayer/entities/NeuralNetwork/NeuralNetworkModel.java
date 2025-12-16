package DomainLayer.entities.NeuralNetwork;

import DomainLayer.interfaces.NeuralNetwork.Layer;
//import InfrastructureLayer.NeuralNetwork.layers.DenseLayer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkModel {
    public void forward(){
        // NEED TO BE IMPLEMENTED
    }
    public double[] predict(double [] in){
        // NEED TO BE COMPLETED
        return in;
    }

    public List<Layer> getLayers(){
        List<Layer> l = new ArrayList<Layer>();
        // NEED TO BE COMPLETED

        return l;
    }
}
