package ApplicationLayer.Controller;

import PresentationLayer.NeuralNetwork.FlightPricePrediction;

public class NeuralController {

    public static void main(String[] args) throws Exception {
        FlightPricePrediction flight = new FlightPricePrediction();
        flight.run();
    }
    }
