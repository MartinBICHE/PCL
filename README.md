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
$ gradle assemble
$ java -jar build/lib/serpython-version.jar
```

## Usage

### Basic Command

To compile a Mini-Python source file:

```bash
$ java -jar build/lib/serpython-version.jar [options] [source-file]
```

### Command-line Options

- `-o [file]`: Specify output file name
- `--help`: Show help message

### Examples

#### Compiling a simple program:

```bash
$ [project-name] -o output.exe example.lang
```

#### Running in debug mode:

```bash
$ [project-name] --debug example.lang
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

