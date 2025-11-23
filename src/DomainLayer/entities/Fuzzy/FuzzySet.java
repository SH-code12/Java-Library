package DomainLayer.entities.Fuzzy;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class FuzzySet {
    private final String name;
    private final MembershipFunction mf;

    public FuzzySet(String name, MembershipFunction mf){
        this.name=name;this.mf=mf;}

    public String getName(){
        return name;}

    public MembershipFunction getMf()
    {return mf;}
}
