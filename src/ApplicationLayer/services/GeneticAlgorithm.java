package ApplicationLayer.services;

import DomainLayer.entities.Chromosome;
import DomainLayer.entities.Population;
import DomainLayer.interfaces.*;
import InfrastructureLayer.selection.RouletteWheelSelection;

import java.util.ArrayList;
import java.util.List;

// here I am implementing genetic algorithem pipline and cycle
// generic class to work with any chromosome type
public class GeneticAlgorithm <G, T extends Chromosome<G>> {


    private int populationSz = 1000;
    private int generationSz = 3000;
    private  double mutationRate = 0.1;

    private IFitnessFunction<T> fitnessFn;

    private SelectionStrategy selectionStrategy;

    public CrossoverStrategy<G, T> getCrossoverStrategy() {
        return crossoverStrategy;
    }

    public void setCrossoverStrategy(CrossoverStrategy<G, T> crossoverStrategy) {
        this.crossoverStrategy = crossoverStrategy;
    }

    public MutationStrategy<G> getMutationStrategy() {
        return mutationStrategy;
    }

    public void setMutationStrategy(MutationStrategy<G> mutationStrategy) {
        this.mutationStrategy = mutationStrategy;
    }

    public ReplacementStrategy<T> getReplacementStrategy() {
        return replacementStrategy;
    }

    public void setReplacementStrategy(ReplacementStrategy<T> replacementStrategy) {
        this.replacementStrategy = replacementStrategy;
    }

    private CrossoverStrategy<G,T> crossoverStrategy;
    private MutationStrategy<G> mutationStrategy;
    private ReplacementStrategy <T> replacementStrategy;

    private Population<T> pop; // population
    private T best;

  int couter =0;
    private double higherFitValue = Double.MIN_VALUE;


    public  GeneticAlgorithm(){
        this.pop = new Population<>();
    }

    public GeneticAlgorithm(int populationSz, int generationSz, double mutationRate, IFitnessFunction<T> fitnessFn, SelectionStrategy selectionStrategy, CrossoverStrategy<G, T> crossoverStrategy, MutationStrategy<G> mutationStrategy, ReplacementStrategy<T> replacementStrategy, Population<T> pop, T best, double higherFitValue) {
        this.populationSz = populationSz;
        this.generationSz = generationSz;
        this.mutationRate = mutationRate;
        this.fitnessFn = fitnessFn;
        this.selectionStrategy = selectionStrategy;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.replacementStrategy = replacementStrategy;
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

    public Population<T> getPop() {
        return pop;
    }

    public void setPop(Population<T> pop) {
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
        if(fitnessFn ==null|| selectionStrategy ==null|| crossoverStrategy==null||mutationStrategy==null|| replacementStrategy==null){
            throw new IllegalStateException("There is missing components, make sure to set (fitness function, selection algo, crossover algo, mutation algo, replacment algo) before calling run function.");
        }
        if(pop==null ){
            throw new IllegalStateException("You don't specify selection strategy, set one please.");
        }
        for(int t =0;t< generationSz;t++){
            System.out.println("Gen"+ (t+1));
            getbestSolutionOfgeneration();
            // Selection

            List<T> children = new ArrayList<>(populationSz);
            while (children.size()<populationSz){
                @SuppressWarnings("unchecked")
                List<T> parents = ((SelectionStrategy<T>)selectionStrategy).select();

                if (parents == null || parents.size() < 2) {

                    System.err.println("Warning: Selection failed to return enough parents. Breaking generation loop.");
                    break;
                }
                // Crossover
                List<T> newGen = crossoverStrategy.getNextGeneration();
                // Mutation

                  for (T c : newGen){

                      mutationStrategy.mutateFirstMethod((Chromosome<G>) c,mutationRate);
                      children.add(c);

                  }





            }
            // replacement
            Population<T> finalPop = replacementStrategy.replace(this.pop,children);
                  this.pop= finalPop;
                  getbestSolutionOfgeneration();


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
