# proml

[![Gitter chat](https://badges.gitter.im/rahulch/proml.png)](https://gitter.im/rahulch/Lobby)

proml is a purely functional, backend agnostic, modular Scala library for developing probabilistic machine learning models with a lot of emphasis on UI and model experimentation.

### proml-core
- Purely Functional Probabilistic Modelling
  - Distributions: Continuous, Discrete
  - Samplers: MeteroPolisHastings 
- Provides type classes for
  - Typesafe Matrix operations
    - Intended:
    <img width="487" alt="image" src="https://user-images.githubusercontent.com/2048960/48650531-6c45e700-e9ab-11e8-853d-69d36494dc14.png">    
    - Compile Error:
    <img width="1245" alt="image" src="https://user-images.githubusercontent.com/2048960/48650590-c2b32580-e9ab-11e8-88ab-eb3d8aa9aa74.png">
  - Backend Agnostic Modeling

### backends
Why stick to one backend when the machine learning models are universal in nature? The emphasis of this library is on the machine learning models, not the backends. So, we abstract this away with a type class. Following are few instances of this type class. You just need to import one of these instances for your experiments.

A Neural Network Model with a new backend is one line change
    <img width="1387" alt="image" src="https://user-images.githubusercontent.com/2048960/48650968-900a2c80-e9ad-11e8-84c6-51f710fdfe80.png">

It very easy to add support for new backends. Contributions are welcome!

- `proml-breeze-backend` : Backend for Breeze. Mostly suited if you are computing on your laptop or experimenting for the first time 
- `proml-tensorflow-backend` : Backend for Tensorflow. You can run it in CPU / GPU mode     

### proml-examples
Contains few modelling examples to get you started. Ideal for people experimenting from IDE's like IntelliJ 

### notebooks
Contains sample Jupyter scala notebooks. This is the best way to play with your models. Also very ideal for teaching/demos. If you are doing any research, we suggest you use these notebooks as your starting guide

#### MC*
- [Power of Random Numbers: Monte Carlo](https://github.com/rahulch/proml/blob/master/notebooks/MonteCarloPI.ipynb)
- [MCMC](https://github.com/rahulch/proml/blob/master/notebooks/MCMC.ipynb)

#### Models
 - [Linear Regression](https://github.com/rahulch/proml/blob/master/notebooks/Linear%20Regression.ipynb)
 - [Bayesian Learning](https://github.com/rahulch/proml/blob/master/notebooks/Bayesian%20Deep%20Learning.ipynb)
 
