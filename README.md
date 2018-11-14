# proml

[![Gitter chat](https://badges.gitter.im/rahulch/proml.png)](https://gitter.im/rahulch/proml)

proml is a purely functional, backend agnostic, modular Scala library for developing probabilistic machine learning models with a lot of emphasis on UI and model experimentation.

### proml-core
- Purely Functional Probabilistic Modelling
- Provides type classes for
  - Typesafe Matrix operations
  - Backend Agnostic Modeling

### backends
Why stick to one backend when the machine learning models are universal in nature? The emphasis of this library is on the machine learning models, not the backends. So, we abstract this away with a type class. Following are few instances of this type class. You just need to import one of these instances for your experiments.

It very easy to add support for new backends. Contributions are welcome!

- `proml-breeze-backend` : Backend for Breeze. Mostly suited if you are computing on your laptop or experimenting for the first time 
- `proml-tensorflow-backend` : Backend for Tensorflow. You can run it in CPU / GPU mode     

### proml-examples
Contains few modelling examples to get you started. Ideal for people experimenting from IDE's like IntelliJ 

### notebooks
Contains sample Jupyter scala notebooks. This is the best way to play with your models. Also very ideal for teaching/demos. If you are doing any research, we suggest you use these notebooks as your starting guide
