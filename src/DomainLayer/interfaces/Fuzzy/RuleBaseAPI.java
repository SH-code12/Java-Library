package DomainLayer.interfaces.Fuzzy;

import DomainLayer.entities.Fuzzy.FuzzyRule;

import java.io.File;

public interface RuleBaseAPI {

    void addRule(FuzzyRule rule);

    void updateRule(int id, FuzzyRule rule );

    void enableRule(int id, boolean enable);

    void removeRule(FuzzyRule rule);

    void setRuleWeight(int id, double weight);

    void saveRulesToFile(String file);

    void loadRulesFromFile(String file) ;
}
