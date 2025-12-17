package DomainLayer.entities.Fuzzy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    private List<FuzzyRule> rules = new ArrayList<>();

    public List<FuzzyRule> getEnabledRules(){
        List<FuzzyRule> enabledRules = new ArrayList<>();
        for(FuzzyRule r: rules){
            if(r.isEnabled())enabledRules.add(r);
        }
        return enabledRules;
    }

    public void addRule(FuzzyRule r){
        rules.add(r);
    }

    public void updateRule(int id, FuzzyRule rule ){
        for(int i =0;i< rules.size();i++){
            if(rules.get(i).getId()==id){
                rules.set(i,rule);
            }
        }
    }

    public FuzzyRule findRulebyId(int id){
        for( FuzzyRule r: rules){
            if(r.getId()==id){
               return r;
            }
        }
        return null;
    }

    public List<FuzzyRule> getRules(){
        return rules;
    }

    public void saveRulesToFile(String filename) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(FuzzyRule r:rules){
                StringBuilder s = new StringBuilder();
                s.append(r.getId()).append("|");
                s.append(r.getWeight()).append("|");
                s.append(r.isEnabled()).append("|");

                int sz =  r.getAntecedents().size();
                for(var entry: r.getAntecedents().entrySet()){
                    s.append(entry.getKey()).append(":").append(entry.getValue());

                    sz--;
                    if(sz>0)s.append(",");
                }
                s.append("|");
                // repeat loop for opertors and outputs
                sz=  r.getOperators().size();
                for(var entry: r.getOperators()){
                    s.append(entry);

                    sz--;
                    if(sz>0)s.append(",");
                }
                s.append("|");
                sz =  r.getConsequents().size();
                for(var entry: r.getConsequents().entrySet()){
                    s.append(entry.getKey()).append(":").append(entry.getValue().toString());

                    sz--;
                    if(sz>0)s.append(",");
                }
                s.append("|");
                sz = r.getCrispConsequents().size();
                for(var entry : r.getCrispConsequents().entrySet()) {
                    s.append(entry.getKey()).append(":").append(entry.getValue());
                    sz--;
                    if(sz > 0) s.append(",");
                }
                writer.write(s.toString());
                writer.newLine();

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRulesFromFile(String filename){
        rules.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line= reader.readLine())!=null){
                String [] parts = line.split("\\|",-1);
                int id = Integer.parseInt(parts[0]);
                FuzzyRule rule = new FuzzyRule(id);
                rule.setWeight(Double.parseDouble(parts[1]));
                rule.setEnabled(Boolean.parseBoolean(parts[2]));

                if(!parts[3].isEmpty()){
                    String[] inputs = parts[3].split(",");
                    for(String in:inputs){
                        String [] pair = in.split(":");
                        if(pair.length==2)rule.addAntecedent(pair[0],pair[1]);

                    }
                }
                if(parts.length>4 && !parts[4].isEmpty()){
                    String [] ops = parts[4].split(",");
                    for(String op: ops){
                        rule.addOperator(op);
                    }
                }
                if(parts.length>5&& !parts[5].isEmpty()){
                    String[] outs = parts[5].split(",");
                    for(String out: outs){
                        String [] pair = out.split(":");
                        if(pair.length==2)rule.addConsequent(pair[0],pair[1]);
                    }
                }
                if(parts.length>6&&!parts[6].isEmpty()){
                    String [] outs = parts[6].split(",");
                    for(String out: outs){
                        String [] pair = out.split(":");
                        if(pair.length==2) rule.addCrispConsequent(pair[0],Integer.parseInt(pair[1]));
                    }
                }

                rules.add(rule);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
