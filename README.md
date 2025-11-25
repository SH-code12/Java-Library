# 🧬 Soft Computing Java Library 

## 📖 Overview
This project is part of a multi-phase **Soft Computing Library** built in **Java**.  
The goal of the library is to provide modular and reusable implementations of major **soft computing techniques**, including:
1. Genetic Algorithms (GA) – Phase 1
2. Fuzzy Logic – Phase 2
3. Neural Networks – Phase 3

Each phase adds one soft computing component and demonstrates it through a **real-world case study**.

This document covers **Phase 1: Genetic Algorithm (GA)**.
---

## ⚙️ Phase 1: Genetic Algorithm (GA)

### 🎯 Objective
In this phase, we implemented a **Genetic Algorithm** capable of solving optimization problems.  
The GA is designed to be **generic**, **configurable**, and **extensible**, supporting different chromosome types and genetic operators.

### 🧩 Features Implemented
- **Chromosome types**:
    - Binary Chromosome
    - Integer Chromosome
    - Floating-Point Chromosome
- **Selection Methods** (at least two):
    - Roulette Wheel Selection
    - Tournament Selection
- **Crossover Methods** (at least three):
    - N-point CrossOver
    - Uniform CrossOver
    - Order-1 CrossOver 

- **Mutation Methods** (for each chromosome type):

- **Replacement Strategies** (three implemented):

- **Hyperparameter Configuration**:
    - Population size
    - Number of generations
    - Crossover rate
    - Mutation rate
    - Selection and crossover type

---
## ⚙️ Phase 2: Fuzzy Logic (FL)

### 🎯 Objective
This phase implements a complete Fuzzy Logic Controller (FLC) pipeline consisting of:

1. Fuzzification:
    - Converts crisp inputs into fuzzy membership values.
2. Inference (Rule Evaluation):
     - Uses min–max Mamdani inference.

3. Aggregation:
     - All fired rules for the same output fuzzy set are aggregated using max operation.

4. Defuzzification:
    - Converts aggregated fuzzy set into a final crisp value

5. Output Generation (Crisp Output):
     - Print Final Output.

6. Full Debug Logging
     - Prints step-by-step computation:

       - Crisp input validation
      
       - Fuzzified values
      
       - Rule firing strengths
      
       - Aggregation results
      
       - Defuzzification process
      
       - Final crisp output
### 🧩 Features Implemented
- **Input Validation**:
  - ClampInput Validator
  - DefaultInput Validator
  - StrictInput Validator

- **Fuzzification**:
  - Simple Fuzzifier
  - 
- **Membership Functions**:
  - Gaussian
  - Trapezoidal
  - Triangular

- **Operators**:
  - AND
    - MIN
    - Product
  - OR
    - MAX
    - SUM
  - Implication
    - Product
    - MIN
  - Aggregation
    - MAX

- **Inference Methods**:
  - Mamdani
  - Sudeno

- **Defuzzification Methods** :
  - Centroid
  - MaxMembershipHight
  - MeanOfMax (MOM)
  - Sugeno Weighted Average

- **Rule Base**:
  - Add Rule
  - Remove Rule 
  - Update Rule
  - Set Weight 
  - Save Rule From File
  - Load Rule From File





