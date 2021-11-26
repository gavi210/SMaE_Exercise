# ATM
This Project is to make an Automated Teller Machine.
Information of the customers taken into account are: **Account Number**, **Password**, and **Bank Account**. 
Operations supported by the ATM are:  **withdraw**, **deposit**, and **view account balance**.

# Project Description
The ATM is composed by three simple classes: 
- Account: it provides attributes and methods to support user operations (withdraw, deposit and balance's view).
- ATM: it is the main project entry point and ancapsulates all the functionalities of the ATM
- OptionMenu: it suppoprts the user interaction with the ATM machine. The interaction is done via simple command line interface.

## Files under review
Since the project is very simple, objective of the review would be to review the implementation of all the three above mentioned classes.
Classes reviewed are: 
1. [Account](/ATM/Account.java)
2. [OptionMenu](/ATM/OptionMenu.java)
3. [ATM](/ATM/ATM.java)

# Roles in the project
Students participating in the exercise are [Maximilian Huwyler](mailto:maximilian.huwyler@uzh.ch) and [Riccardo Rigoni](mailto:riccardo.rigoni@uzh.ch). 
Both participants will act as ***reviewers*** and ***report writers***. 

# Review Methodology
Maximilian Huwyler will analyse the [OptionMenu.java](/ATM/OptionMenu.java) file, and Riccardo Rigoni the [Account.java](/ATM/Account.java) file. 
[ATM.java](/ATM/ATM.java) will be investigated together.

Reviewers process will be done as follow:
1. **Initial Code Overview**: goal is to properly understand the code behaviour,
2. **Manual identification of aspects to be improved**: with the knowledge of the code behaviour, lack in code quality could be detected. The dimentions of code quality investigated are mentioned below,
3. **Suggestion for Improvements**: reviewer suggest possible code changes,
4. **Identification of Warnings with automatic tools**: PMD tool is used to further investigate defects in the code.

## Manually investigated Quality Dimensions
During the manul review the reviewers will look at: 
- **Code Clarity**: variable and method name, code organization, indentation,
- **Ease of Modification**: how easily would be to change the code,
- **Logic**: are all cases edge cases covered?, correctness of loop and branches, are conditions meaningfull?,
- **Error Handling**: correctness and soundness of error handling,
- **Design Decisions**: code structure, architectural patterns used.   

## PMD
As suggested in the project specification, the [PMD](https://pmd.github.io) tool is used to discover faults in the code. 
When running the PMD tool, the following set of predefined code quality dimensions are taken into account:
- bestpractices
- codestyle
- design
- errorprone
- documentation
- performance
- security

This dimensions could be customized and are specified in the [PMD_RuleSet](/PMD_RuleSet.xml) file.



# Defects Found
Follows a description of each fault found per java file.

## OptionMenu.java
Here will be done by Maximilian

Infinite loop maybe possible!

## Account.java
Here will be done by Riccardo

## ATM.java 
If time done



To run the project\
1.Download all the code files (total 3)\
2.Store them in a single folder\
3.Either use your Java IDE e.g. Eclipse ,InteliJ or NetBeans\
4.Or in Windows open command prompt and go to project Directory\
1. run command : javac ATM.java\
2. After completion your folder will contain 3 .class files\
3. In command prompt run command: java ATM\
4. Project will start running
