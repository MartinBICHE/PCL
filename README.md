# Serpython Compiler

## Table of Contents

1. [Introduction](#introduction)
1. [Team](#team)
1. [Features](#features)
1. [Installation](#installation)
1. [Usage](#usage)
1. [Examples](#examples)
1. [Testing](#testing)
1. [Acknowledgements](#acknowledgements)

---

## Introduction

The **Serpython Compiler** is a Mini-Python compiler designed to compile. It translates Mini-Python (a subset of Python) source
code into AST.

### Team

- Timothée BRILLAUD
- Zoé VERNICOS
- Martin BICHE
- Théophile PICHARD

### Why use Serpython?

- Subset of Python.
- A good taste of experimentation.

## Features

- **Cross-platform:** Runs on all platforms supporting a recent version of Java.
- **Error Reporting:** Detailed syntax and semantic error messages.


## Installation

### Prerequisites

- Java JDK 21
- Gradle >8

#### Example:

```bash
# pacman -Sy openjdk gradle
```

### Installing the Compiler

Clone the repository and install any required dependencies:

```bash
$ git clone https://gibson.telecomnancy.univ-lorraine.fr/projets/2425/compil/pcl-grp23.git
$ cd pcl-grp23
$ gradle build
$ java -jar build/libs/serpython-all.jar
```

## Usage

### Basic Command

To compile a Mini-Python source file:ava -jar build/lib/serpython-all.jar

```bash
$ java -jar build/libs/serpython-all.jar [options] [source-file]
```

### Command-line Options

```
usage: 
 -c,--code <code>       Specify the input code as a string (-c/-f mutually exclusive)
 -f,--file <filename>   Specify the input file (-c/-f mutually exclusive)
 -p,--parse             Parse the input (default behavior)
 -t,--tokenize          Tokenize the input and display tokens
 -v,--visualize         Visualize the parse tree as Mermaid code
```

### Examples

#### Generate the tokens of a source file

```bash
java -jar build/libs/serpython-all.jar -t -f <source-file>
```

or 

```bash
java -jar build/libs/serpython-all.jar -t -c "<inline-code>"
```

Quick example:

```bash
java -jar build/libs/serpython-all.jar -t -f src/test/resources/hello_world.mpy
```

#### Parse the source file

```bash
java -jar build/libs/serpython-all.jar -p -f src/test/resources/hello_world.mpy
```

Add the `--visualize` flag to get a Mermaid diagram of the AST generated.

```bash
java -jar build/libs/serpython-all.jar -p -v -f src/test/resources/hello_world.mpy
```

## Examples

Here are some sample programs you can compile using [Project Name]:

1. **Hello World:**

```python
print "Hello, world"

```

2. **Fibonacci Sequence:**

```python
def fibo(n):
    if (n <= 1):
        return 1
    else:
        return fibo(n-1) + fibo (n-2)


print "Fibo(8): " + fibo(8)

```

For more examples, refer to the `src/test/resources/` directory.

## Testing

```bash
$ gradle check
```

## Acknowledgements

- OpenJDK, JUnit, Gradle used in this project

