package ApplicationLayer.services;

import DomainLayer.entities.NeuralNetwork.NeuralNetworkModel;
import DomainLayer.interfaces.NeuralNetwork.Layer;
import DomainLayer.interfaces.NeuralNetwork.LossFunction;

import java.util.List;

public class NeuralService {

    public LossFunction getLossFunction() {
        return lossFunction;
    }

    public void setLossFunction(LossFunction lossFunction) {
        this.lossFunction = lossFunction;
    }

    private LossFunction lossFunction;

    public void fit(NeuralNetworkModel nn, double[][] in,double[][] out,int epochs,double lr ){
        double totalEpochloss =0;
        for(int epoch=1;epoch<=epochs;epoch++){
            totalEpochloss =0;
            for(int sample =0;sample<in.length;sample++){
                nn.forward();
                double [] prediction = nn.predict(in[sample]);
                double loss =  lossFunction.calc_loss(prediction,out[sample]);
                totalEpochloss += loss;

                double[]  lossVector = lossFunction.calc_deravtive(prediction, out[sample]);
                List<Layer> layers = nn.getLayers();
                for(int i = layers.size()-1;i>=0;i--){
                Layer ll = layers.get(i);
                    lossVector = ll.backward(lossVector,lr);
                }

            }
            if(epoch%100 ==0) {
                double avgLoss = totalEpochloss/in.length;
                System.out.println("Epoch "+ (epoch)+" , average loss is: "+ avgLoss);


            }
        }

    }
}
