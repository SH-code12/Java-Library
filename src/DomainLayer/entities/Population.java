package DomainLayer.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Population <T extends Chromosome> implements Iterable<T>{
    private List<T> chromosomes;

    public Population() {
        this.chromosomes = new ArrayList<>();
    }
    public Population(List<T> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public List<T> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(List<T> chromosomes) {
        this.chromosomes = chromosomes;
    }
    public void addChromosome(T chromosome) {
        this.chromosomes.add(chromosome);
    }

    public int size() {
        return chromosomes.size();
    }

    @Override
    public Iterator<T> iterator() {
        return chromosomes.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}