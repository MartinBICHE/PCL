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
code into [output (e.g., machine code, bytecode, intermediate representation)] and includes optimizations for [specific features or targets].

### Team

- Timothée BRILLAUD
- Zoé VERNICOS
- Martin BICHE
- Théophile PICHARD

### Why use Serpython?

- Fast and efficient compilation
- Support for [languages or standards]
- [Any unique features or goals]

## Features

- **Cross-platform:** Runs on [list of supported platforms]
- **Optimization:** Implements advanced optimizations like [list of optimizations: e.g., constant folding, dead code elimination]
- **Error Reporting:** Detailed syntax and semantic error messages
- **[Other features]**

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
$ java -jar build/lib/serpython-all.jar
```

## Usage

### Basic Command

To compile a Mini-Python source file:

```bash
$ java -jar build/lib/serpython-all.jar [options] [source-file]
```

### Command-line Options

```
usage: 
 -c,--code <code>       Specify the input code as a string
 -f,--file <filename>   Specify the input file
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
java -jar build/libs/serpython-all.jar -t -c <inline-code>
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

```language
// Example code for Hello World in [language]
```

2. **Fibonacci Sequence:**

```language
// Example code for Fibonacci in [language]
```

For more examples, refer to the `examples/` directory.

## Testing

```bash
$ gradle check
```

### Reporting Issues

If you encounter any issues or have feature requests, please open an issue on the GitHub repository.

## Acknowledgements

- OpenJDK, JUnit, Gradle used in this project

