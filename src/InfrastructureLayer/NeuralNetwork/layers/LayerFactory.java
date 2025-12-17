package InfrastructureLayer.NeuralNetwork.layers;

import DomainLayer.entities.NeuralNetwork.LayerConfig;
import DomainLayer.interfaces.NeuralNetwork.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerFactory {

    public static List<Layer> createLayers(List<LayerConfig> configs){
        List<Layer> layers = new ArrayList<>();
        for(LayerConfig cfg : configs){
            layers.add(new DenseLayer(
                    cfg.inputSize,
                    cfg.outputSize,
                    cfg.activation,
                    cfg.initializer,
                    cfg.optimizer
            ));
        }
        return layers;
    }

}
