# Start Jupyter server

Follow the instructions below to install the dependencies

Once you are done with the setup, you can start Jupyter as:
```shell
jupyter notebook
```

This should open a browser where you can see the notebooks 

# Dependencies

## Jupyter 
http://jupyter.org/install
```shell
# If you have Python 3 installed (which is recommended):

python3 -m pip install --upgrade pip
python3 -m pip install jupyter

# If you have Python 2 installed:
python -m pip install --upgrade pip
python -m pip install jupyter

```

## Jupyter Scala Kernel

Follow instructions in http://almond-sh.github.io/almond/stable/docs/quick-start-install

```shell
$ SCALA_VERSION=2.12.7 ALMOND_VERSION=0.1.11
$ coursier bootstrap \
    -i user -I user:sh.almond:scala-kernel-api_$SCALA_VERSION:$ALMOND_VERSION \
    sh.almond:scala-kernel_$SCALA_VERSION:$ALMOND_VERSION \
    -o almond
$ ./almond --install    
```
* Note: 0.1.11-SNAPSHOT was used as of this commit, as 0.1.11 is not released yet

```shell
# 0.1.11-SNAPSHOT Setup
git clone git@github.com:almond-sh/almond.git
cd almond
git checkout eed7aa2a0911a2871c6b9b134785c5c921bdae4a
sbt publishLocal

SCALA_VERSION=2.12.7 ALMOND_VERSION=0.1.11

coursier bootstrap \
    -i user -I user:sh.almond:scala-kernel-api_$SCALA_VERSION:$ALMOND_VERSION \
    sh.almond:scala-kernel_$SCALA_VERSION:$ALMOND_VERSION \
    -o almond
./almond --install --force
```

## Evilplot

https://cibotech.github.io/evilplot

```
git clone git@github.com:cibotech/evilplot.git
git checkout 294ede802ce04f0610666b0ca89927aed70cf99b
cd evilplot
sbt publishLocal
```

## proml

```
git clone git@github.com:rahulch/proml.git
git checkout efff7b9891d9f58ae3c146cbe3e2aef41720bfed
cd proml
sbt publisLocal
```
