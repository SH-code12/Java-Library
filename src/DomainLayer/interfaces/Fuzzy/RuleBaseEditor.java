package DomainLayer.interfaces.Fuzzy;

import DomainLayer.entities.Fuzzy.FuzzyRule;

import java.io.File;

public interface RuleBaseEditor {

    void addRule(FuzzyRule rule);
    void removeRule(FuzzyRule rule);
    void enableRule(FuzzyRule rule, boolean enable);
    void setWeight(FuzzyRule rule, double w);
    void saveToFile(File file) throws java.io.IOException;
    void loadFromFile(File file) throws java.io.IOException;
}
