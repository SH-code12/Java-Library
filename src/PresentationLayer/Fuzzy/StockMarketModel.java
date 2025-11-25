package PresentationLayer.Fuzzy;

import ApplicationLayer.services.FuzzySystem;
import InfrastructureLayer.Fuzzy.membership.Gaussian;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

public class StockMarketModel {
    int priceTrend,volume,volatility;
    public  void buildStock(){


        FuzzySystem system = new FuzzySystem();
        Trapezoidal Low,High,Stable,Negative,Positive,Risky;
        Gaussian Medium;
        Triangular Neutral;

        system.addInputVariable();
    }
}
