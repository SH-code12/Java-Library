package ApplicationLayer.Controller;

import PresentationLayer.NeuralNetwork.FlightPricePrediction;

public class NeuralController {

    public static void main(String[] args) throws Exception {
        FlightPricePrediction flight = new FlightPricePrediction();
        String dataPath = " D://SHaHD//4th_first term//Soft Computing//Assignments//Java_Library//java-library//resources//Clean_Dataset.csv";
        flight.run();
    }
    }
