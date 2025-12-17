package DomainLayer.interfaces.NeuralNetwork;

public interface LossFunction {

    double calc_loss(double[] pred, double []actual);
    double[] calc_deravtive( double[] pred, double [] actual );
}
