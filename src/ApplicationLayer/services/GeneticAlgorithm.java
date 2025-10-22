package ApplicationLayer.services;

import DomainLayer.interfaces.IFitnessFunction;
import DomainLayer.interfaces.SelectionStrategy;

import java.util.List;

// here I am implementing genetic algorithem pipline and cycle
// generic class to work with any chromosome type
public class GeneticAlgorithm <T> {

    private int populationSz = 1000;
    private int generationSz = 3000;
    private  double mutationRate = 0.1;

    private IFitnessFunction<T> fitnessFn;

    private SelectionStrategy selectionStrategy;

    private List<T> pop; // population
    private T best;

    private double higherFitValue = Double.MIN_VALUE;

    public  GeneticAlgorithm(){

    }


    public GeneticAlgorithm(int populationSz, int generationSz, double mutationRate, IFitnessFunction<T> fitnessFn, SelectionStrategy selectionStrategy, List<T> pop, T best, double higherFitValue) {
        this.populationSz = populationSz;
        this.generationSz = generationSz;
        this.mutationRate = mutationRate;
        this.fitnessFn = fitnessFn;
        this.selectionStrategy = selectionStrategy;
        this.pop = pop;
        this.best = best;
        this.higherFitValue = higherFitValue;
    }

    public int getPopulationSz() {
        return populationSz;
    }

    public void setPopulationSz(int populationSz) {
        this.populationSz = populationSz;
    }

    public int getGenerationSz() {
        return generationSz;
    }

    public void setGenerationSz(int generationSz) {
        this.generationSz = generationSz;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public IFitnessFunction<T> getFitnessFn() {
        return fitnessFn;
    }

    public void setFitnessFn(IFitnessFunction<T> fitnessFn) {
        this.fitnessFn = fitnessFn;
    }

    public SelectionStrategy getSelectionStrategy() {
        return selectionStrategy;
    }

    public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
    }

    public List<T> getPop() {
        return pop;
    }

    public void setPop(List<T> pop) {
        this.pop = pop;
    }

    public T getBest() {
        return best;
    }

    public void setBest(T best) {
        this.best = best;
    }

    public double getHigherFitValue() {
        return higherFitValue;
    }

    public void setHigherFitValue(double higherFitValue) {
        this.higherFitValue = higherFitValue;
    }

    public T run(){
        if(fitnessFn ==null){
            throw new IllegalStateException("There is no fitness function, set one please.");
        }
        for(int t =0;t< generationSz;t++){
            getbestSolutionOfgeneration();
            // complete other steps of selection , crossover , mutation


        }
        return best;
    }

    private  void getbestSolutionOfgeneration(){
        for(T sol: pop){
            double fitness = fitnessFn.getFitnessVal(sol);
            if(fitness>higherFitValue){
                higherFitValue = fitness;
                best = sol;

            }

        }
    }
}
