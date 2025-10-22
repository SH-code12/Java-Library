package DomainLayer.entities;

import java.util.List;

public class Population <T extends Chromosome>{
    private List<T> chromosomes;
    public List<T> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(List<T> chromosomes) {
        this.chromosomes = chromosomes;
    }



    public Population(List<T> chromosomes) {
        this.chromosomes = chromosomes;
    }


}
