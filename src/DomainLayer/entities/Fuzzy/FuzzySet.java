package DomainLayer.entities.Fuzzy;

import DomainLayer.interfaces.Fuzzy.MembershipFunction;

public class FuzzySet {
    private final String name;
    private  MembershipFunction mf;

    public FuzzySet(String name, MembershipFunction mf){
        this.name=name;
        this.mf=mf;
    }
    public void setMembershipFunction(MembershipFunction membershipFunction) {
        this.mf = membershipFunction;
    }

    public String getName(){
        return name;}

    public MembershipFunction getMf() {
        return mf;
    }
}
