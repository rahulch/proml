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
git checkout 0228db85c03ab448e3f810dc6b9680c2c1b5bba5
cd almond
sbt publisLocal

SCALA_VERSION=2.12.7 ALMOND_VERSION=0.1.11-SNAPSHOT

coursier bootstrap \
    -i user -I user:sh.almond:scala-kernel-api_$SCALA_VERSION:$ALMOND_VERSION \
    sh.almond:scala-kernel_$SCALA_VERSION:$ALMOND_VERSION \
    -o almond
./almond --install 
```

## Evilplot

https://cibotech.github.io/evilplot

```
git clone git@github.com:cibotech/evilplot.git
git checkout 294ede802ce04f0610666b0ca89927aed70cf99b
cd evilplot
sbt publisLocal
```
