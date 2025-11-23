package DomainLayer.interfaces.Fuzzy;

public abstract class MembershipFunction {
    protected String name; // like "Low", "Medium", "High"

    public MembershipFunction(String n){this.name = n;}
    public String getName(){return this.name;}
    public abstract double compute(double x);  // abstract -> all childs must implement it. 

}  
    
 