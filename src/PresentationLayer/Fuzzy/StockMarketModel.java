package PresentationLayer.Fuzzy;

import ApplicationLayer.services.FuzzySystem;
import DomainLayer.entities.Fuzzy.FuzzyRule;
import DomainLayer.entities.Fuzzy.FuzzySet;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import InfrastructureLayer.Fuzzy.membership.Gaussian;
import InfrastructureLayer.Fuzzy.membership.Trapezoidal;
import InfrastructureLayer.Fuzzy.membership.Triangular;

public class StockMarketModel {
    private FuzzySystem system;

    public StockMarketModel() {
        this.system = new FuzzySystem();
        buildStock();
    }

    private  void buildStock(){
        LinguisticVariable trend = new LinguisticVariable("PriceTrend",-10,10);
        trend.addFuzzySet(new FuzzySet("Negative", new Trapezoidal(-15, -10, -2, 0)));
        trend.addFuzzySet(new FuzzySet("Neutral", new Triangular(-2, 0, 2)));
        trend.addFuzzySet(new FuzzySet("Positive", new Trapezoidal(0, 2, 10, 15)));

        system.addInputVariable(trend);


        LinguisticVariable volume = new LinguisticVariable("Volume", 0, 1000000);

        volume.addFuzzySet(new FuzzySet("Low", new Trapezoidal(0, 0, 200000, 400000)));
        volume.addFuzzySet(new FuzzySet("Medium", new Gaussian(500000, 100000))); // Mean, Sigma
        volume.addFuzzySet(new FuzzySet("High", new Trapezoidal(600000, 800000, 1000000, 1000000)));

        system.addInputVariable(volume);

        LinguisticVariable decision = new LinguisticVariable("Decision", 0, 100);

        decision.addFuzzySet(new FuzzySet("Sell", new Trapezoidal(0, 0, 30, 50)));
        decision.addFuzzySet(new FuzzySet("Hold", new Triangular(40, 50, 60)));
        decision.addFuzzySet(new FuzzySet("Buy", new Trapezoidal(50, 70, 100, 100)));

        system.setOutputVariable(decision);

        // Define Rules here example rule

    }
}
