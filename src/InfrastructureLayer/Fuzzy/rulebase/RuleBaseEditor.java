package InfrastructureLayer.Fuzzy.rulebase;

import DomainLayer.entities.Fuzzy.FuzzyRule;

import DomainLayer.entities.Fuzzy.RuleBase;
import DomainLayer.interfaces.Fuzzy.RuleBaseAPI;


public class RuleBaseEditor implements RuleBaseAPI {


    public RuleBaseEditor(RuleBase rb){
        this.rb = rb;
    };

    private final RuleBase rb;


   @Override
    public void addRule(FuzzyRule r){
        rb.addRule(r);
    }

    @Override
    public void updateRule(int id, FuzzyRule rule ){
       rb.updateRule(id, rule);
    }


    @Override
    public void enableRule(int id, boolean enable){
        FuzzyRule r = rb.findRulebyId(id);
        if (r != null) {
            r.setEnabled(enable);
        }
    }

    @Override
    public void removeRule(FuzzyRule rule) {
        rb.getRules().removeIf(r -> r.getId() == rule.getId());
    }

    @Override
    public void setRuleWeight(int id, double weight) {
        FuzzyRule r = rb.findRulebyId(id);
        if (r != null) {
            r.setWeight(weight);
        }
    }

    @Override
    public void saveRulesToFile(String filename) {
       rb.saveRulesToFile(filename);

    }

    @Override
    public void loadRulesFromFile(String filename){
       rb.loadRulesFromFile(filename);

    }

}
