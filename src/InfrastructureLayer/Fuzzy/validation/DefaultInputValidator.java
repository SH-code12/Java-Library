package InfrastructureLayer.Fuzzy.validation;

import DomainLayer.interfaces.Fuzzy.InputValidator;
import DomainLayer.entities.Fuzzy.LinguisticVariable;
import DomainLayer.entities.Fuzzy.FuzzySet;
import java.util.*;

public class DefaultInputValidator implements InputValidator {
    private final int samples;

    public DefaultInputValidator(){
        this(101);
    }

    public DefaultInputValidator(int samples){
        this.samples = Math.max(11, samples);
    }

    private String findPreferredSetName(LinguisticVariable lv){
        for (String p : Arrays.asList("Medium","Normal","Default")) {
            if (lv.getFuzzySets().containsKey(p)) {
                return p;
            }
        }
        for (String key : lv.getFuzzySets().keySet()){
            if (key.equalsIgnoreCase("medium")||key.equalsIgnoreCase("normal")){
                return key;
            }
        }
        return null;
    }

    private double chooseRepresentativeForVariable(LinguisticVariable lv) {
        double min = lv.getMin(), max = lv.getMax();
        double step = (max - min) / (samples - 1);
        String preferred = findPreferredSetName(lv);
        double bestX = (min + max) / 2.0, bestPeak = -1.0;
        for (Map.Entry<String, FuzzySet> fsEntry : lv.getFuzzySets().entrySet()) {
            String setName = fsEntry.getKey();
            FuzzySet set = fsEntry.getValue();
            double localPeak = -1.0, localArgmax = min;
            for (int i=0;i<samples;i++){
                double x=min+i*step;
                double mu=set.getMf().membership(x);
                if(mu>localPeak){
                    localPeak=mu;
                    localArgmax=x;
                }
            }
            if (preferred!=null && preferred.equalsIgnoreCase(setName)) {
                bestX = localArgmax;
                bestPeak = localPeak + 1e-9;
                break;
            }
            if (localPeak > bestPeak) {
                bestPeak = localPeak;
                bestX = localArgmax;
            }
        }
        if (bestPeak < 0) {
            return (min+max)/2.0;
        }
        return bestX;
    }

    @Override
    public Map<String, Double> validate(Map<String, Double> inputs, Map<String, LinguisticVariable> variables){
        Map<String, Double> out = new LinkedHashMap<>();
        for (Map.Entry<String, LinguisticVariable> e : variables.entrySet()) {
            String name = e.getKey();
            LinguisticVariable lv = e.getValue();
            Double v = inputs.get(name);
            if (v != null) {
                out.put(name, Math.max(lv.getMin(), Math.min(lv.getMax(), v)));
            }
            else {
                out.put(name, chooseRepresentativeForVariable(lv));
            }
        }
        return out;
    }

}
