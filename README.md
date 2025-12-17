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
#      Test Nueral Networl
```
"C:\Program Files\Java\jdk-21\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.3\lib\idea_rt.jar=65312:C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.3\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "D:\SHaHD\4th_first term\Soft Computing\Assignments\Java_Library\java-library\out\production\java-library" Main
Choose System to Run:
1. Genetic Algorithm
2. Fuzzy Logic
3. Neural Network Algorithm
3
Loading Data...

Data Loaded. Total rows: 300153
Sampled rows: 50000
Validate Data..

Normalizing features...
Normalizing target...
Splitting data...
Enter epochs(100-500):
120
Enter batch size(16-256):
16
Enter learning rate(0.01,0.001,0.0001):
0.0005
Choice Optimizer: 
1.Adam Optimizer
2.SGD Optimizer

1
Choice LossFunction: 
1.MSELoss 
2.CrossEntropyLoss 

1
Enter number of layers:
4
Note: :Last Layer should have 1 neuron 

Layer 1 neurons(32/16/1):
60
Choice Activation: 
1.Sigmoid 
2.Relu
3.Tanh
4.Linear 

1
Choice Weight initializer: 
1.RandomUniform
2.Xavier
3.HeInitializer

2
Note: :Last Layer should have 1 neuron 

Layer 2 neurons(32/16/1):
40
Choice Activation: 
1.Sigmoid 
2.Relu
3.Tanh
4.Linear 

2
Choice Weight initializer: 
1.RandomUniform
2.Xavier
3.HeInitializer

3
Note: :Last Layer should have 1 neuron 

Layer 3 neurons(32/16/1):
20
Choice Activation: 
1.Sigmoid 
2.Relu
3.Tanh
4.Linear 

3
Choice Weight initializer: 
1.RandomUniform
2.Xavier
3.HeInitializer

2
Note: :Last Layer should have 1 neuron 

Layer 4 neurons(32/16/1):
1
Choice Activation: 
1.Sigmoid 
2.Relu
3.Tanh
4.Linear 

4
Choice Weight initializer: 
1.RandomUniform
2.Xavier
3.HeInitializer

2
Start Debugging ...

Training...
Epoch 1 Loss: 0.8980781684191504
Epoch 2 Loss: 0.9778403883824632
Epoch 3 Loss: 0.9735973700779962
Epoch 4 Loss: 0.9578501610138653
Epoch 5 Loss: 0.967567338380716
Epoch 6 Loss: 0.9592778690760504
Epoch 7 Loss: 0.9576407482462519
Epoch 8 Loss: 0.9635123638737465
Epoch 9 Loss: 0.9577271676138275
Epoch 10 Loss: 0.9418208749307873
Epoch 11 Loss: 0.950082448581447
Epoch 12 Loss: 0.9670438222063804
Epoch 13 Loss: 0.965435821640731
Epoch 14 Loss: 0.9591096203899504
Epoch 15 Loss: 0.9395742743492723
Epoch 16 Loss: 0.9570962379748175
Epoch 17 Loss: 0.9489964948590858
Epoch 18 Loss: 0.9612459435606177
Epoch 19 Loss: 0.9627810115036924
Epoch 20 Loss: 0.9632202696030064
Epoch 21 Loss: 0.9728710143311073
Epoch 22 Loss: 0.9683897762016234
Epoch 23 Loss: 0.9820467877612149
Epoch 24 Loss: 0.9775441622693728
Epoch 25 Loss: 0.9818517892709818
Epoch 26 Loss: 0.9912188299895537
Epoch 27 Loss: 0.9874112266004053
Epoch 28 Loss: 0.9931405151402792
Epoch 29 Loss: 0.9887853494083921
Epoch 30 Loss: 0.9905145365198156
Epoch 31 Loss: 0.9808458017133427
Epoch 32 Loss: 0.9842157072353064
Epoch 33 Loss: 0.9780842178022245
Epoch 34 Loss: 0.9741895410468302
Epoch 35 Loss: 0.9828461997398341
Epoch 36 Loss: 0.9791153295773549
Epoch 37 Loss: 0.9827964890365045
Epoch 38 Loss: 0.9813872189522079
Epoch 39 Loss: 0.9704255718997948
Epoch 40 Loss: 0.9804459108408592
Epoch 41 Loss: 0.9734359512422314
Epoch 42 Loss: 0.9743888895957452
Epoch 43 Loss: 0.9756355421866918
Epoch 44 Loss: 0.9732789333910341
Epoch 45 Loss: 0.9756649097616149
Epoch 46 Loss: 0.9697255083345417
Epoch 47 Loss: 0.9715103793531769
Epoch 48 Loss: 0.9801660673227232
Epoch 49 Loss: 0.9746829686977859
Epoch 50 Loss: 0.9743299707638593
Epoch 51 Loss: 0.9794189900392551
Epoch 52 Loss: 0.9827577166145517
Epoch 53 Loss: 0.9799928835380819
Epoch 54 Loss: 0.9703067633853462
Epoch 55 Loss: 0.9735624924750531
Epoch 56 Loss: 0.9742476925021254
Epoch 57 Loss: 0.9805944013092408
Epoch 58 Loss: 0.9745146195489544
Epoch 59 Loss: 0.9821317770974929
Epoch 60 Loss: 0.9739455735491678
Epoch 61 Loss: 0.9754178751550635
Epoch 62 Loss: 0.9775481963405122
Epoch 63 Loss: 0.9707716908200369
Epoch 64 Loss: 0.9662058467182787
Epoch 65 Loss: 0.965582468553776
Epoch 66 Loss: 0.9667103214456414
Epoch 67 Loss: 0.9846104604641516
Epoch 68 Loss: 0.9717036985436381
Epoch 69 Loss: 0.9726989493018491
Epoch 70 Loss: 0.972503408743877
Epoch 71 Loss: 0.9781972354206669
Epoch 72 Loss: 0.9853997750622929
Epoch 73 Loss: 0.9747726977437576
Epoch 74 Loss: 0.9735430800440341
Epoch 75 Loss: 0.974373245660242
Epoch 76 Loss: 0.9804305269860679
Epoch 77 Loss: 0.9778929088608306
Epoch 78 Loss: 0.9782252822833473
Epoch 79 Loss: 0.9760944799726305
Epoch 80 Loss: 0.9812328417461347
Epoch 81 Loss: 0.9758288542567314
Epoch 82 Loss: 0.9790666614023538
Epoch 83 Loss: 0.9841212658995231
Epoch 84 Loss: 0.9825039050831065
Epoch 85 Loss: 0.9772035708584992
Epoch 86 Loss: 0.9794221139424162
Epoch 87 Loss: 0.9811861551097923
Epoch 88 Loss: 0.9824544680971772
Epoch 89 Loss: 0.9787230832169805
Epoch 90 Loss: 0.9835102892107627
Epoch 91 Loss: 0.9885286263343008
Epoch 92 Loss: 0.9838514775845322
Epoch 93 Loss: 0.9774501239360681
Epoch 94 Loss: 0.98131857552945
Epoch 95 Loss: 0.9819271717570102
Epoch 96 Loss: 0.9816489058503408
Epoch 97 Loss: 0.9893179144465057
Epoch 98 Loss: 0.9835261936460702
Epoch 99 Loss: 0.986835635858221
Epoch 100 Loss: 0.9829923015791273
Epoch 101 Loss: 0.9926107990332377
Epoch 102 Loss: 0.9863327661719126
Epoch 103 Loss: 0.9934988492897704
Epoch 104 Loss: 0.9769046943144911
Epoch 105 Loss: 0.9817807452710835
Epoch 106 Loss: 0.9863953001151021
Epoch 107 Loss: 0.9920350704931221
Epoch 108 Loss: 0.978850980970627
Epoch 109 Loss: 0.9898276800870717
Epoch 110 Loss: 0.9801764528855865
Epoch 111 Loss: 0.9832471048299143
Epoch 112 Loss: 0.9859838666981022
Epoch 113 Loss: 0.9904615282283321
Epoch 114 Loss: 0.9787786505015909
Epoch 115 Loss: 0.9894807942146564
Epoch 116 Loss: 0.984339778529501
Epoch 117 Loss: 0.9904529607286042
Epoch 118 Loss: 0.988697889561151
Epoch 119 Loss: 0.9919847686370787
Epoch 120 Loss: 0.9847084785133773
Predictions on test set (denormalized):
Predicted price: $6,687
Actual price   : $5,800
Absolute error : $887
----------------------------------
Predicted price: $5,561
Actual price   : $6,909
Absolute error : $1,348
----------------------------------
Predicted price: $6,812
Actual price   : $10,774
Absolute error : $3,962
----------------------------------
Predicted price: $62,091
Actual price   : $67,004
Absolute error : $4,913
----------------------------------
Predicted price: $62,091
Actual price   : $54,608
Absolute error : $7,483
----------------------------------
Model Evaluation:
MAE : $0.13
RMSE : $0.23
R²  : 0.9501
Model saved and loaded successfully.

Process finished with exit code 0

````





