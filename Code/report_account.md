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
1. **Initial Code Overview**: goal is to properly understand the code behaviour
2. **Identification of Aspects to be improved**: with the knowledge of the code behaviour, lack in code quality could be detected
3. **Suggestion for Improvements**: reviewer suggest possible code changes.
4. **Identification of Warnings with automatic tools**: (which tool to be used?) is used to further investigate imperfections in the code.

## Tools for Automatic Code Review
As suggested in the project specification, the [PMD]() tool is used to discover faults in the code. 
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
From an initial view of the class emerges that the code is not well structured and no much ***cleaning*** has been done before submitting the code. Lots of code is duplicate and important security threats have not been verified. 
Follows a more in-depth descriptions of the defects found.

### Manually detected Defects
| Lines               | Defect                                                                                                                        | Defect Type          | Reccomendation                               | Occurrences |   
|---------------------|-------------------------------------------------------------------------------------------------------------------------------|----------------------|----------------------------------------------|-------------|
|  15 - 16            | Default constructor would allow to create empty account with no **pin** and **number** associated                             | Logic                | Remove it                                    | 1           |  
| 18 - 21             | Constructor will not assign value to **checkingBalance** and **savingBalance**                                                | Logic                | Remove it                                    | 1           |  
| 30 - 43             | customerNumber and customer pin should not be changed                                                                         | Logic                | Remove Setters                               | 2           |  
| 56 - 84             | unused methods                                                                                                                | Code Clarity         | Remove them                                  | 6           |  
| 86 - 235            | misleading method names                                                                                                       | Code Clarity         | Rename                                       | 5           |   
| 86 - 235            | the same methods do two actions: interact with user and update balance's value                                                | Ease of Modification | Consider to split into two different methods | 6           |   
| 86 -235             | no way to exit from the loop if user wants to exit the operation                                                              | Logic                | Provide a way to terminate the operation     | 6           |  
| 86 - 235            | Lot of duplicate code                                                                                                         | Ease of Modification | Refactor the methods to avoid duplicate      | 6           |   
| 93 - 95 & 115 - 117 | update of balance values is not atomic: if exception thrown during the update, balance values could result to be inconsistent | Logic                | Make update operation safe                   | 6           |   
| 171 - 235           | Method too complicated                      | Ease of Modification | Split into submethods | 2 |  
| 141 - 235           | Duplicate code for user interaction         | Ease of Maintenance & Design Decisions | Consider encapsulate user interaction and input validation in a method | 6 |

### Automatically detected Defects
| Lines                 | Defect                                                                                                                            | Defect Type                  | Reccomendation                                                | Occurrences |
|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------|------------------------------|---------------------------------------------------------------|-------------|
| -                     | Classes, Fields and Methods comments are required                                                                                 | CommentRequired              | Add Comments                                                  | 14          |
| 9 - 10               | Avoid using redundant field initializer for 'checkingBalance' and 'savingBalance'                                                 | RedundantFieldInitializer    | Remove redundant initializers                                 | 2           |
| 12 - 13               | To avoid mistakes add a comment at the beginning of the input field if you want a default access modifier (2)                     | CommentDefaultAccessModifier | Add comments                                                  | 2           |
| 15 - 29               | Add comments to constructors                                                                                                      | Comment Required             | Add comments                                                  | 3           |
| 30 - 39               | Linguistics Antipattern - The setter 'setCustomerNumber' and 'setPinNumber' should not return any type except void linguistically | LinguisticNaming             | Rename methods                                                | 2           |
| 57 ; 62 ; 67 ; 72        | Useless parenthesis                                                                                                               | UselessParetheses            | Remove them                                                   | 4           |
| 86 ; 107 ; 128 ; 149 ; 171 | Linguistics Antipattern - The getter 'getCheckingWithdrawInput' should not return void linguistically                             | LinguisticNaming             | Rename method                                                 | 5           |
| 90 ; 101 ; 111 ; 119 | Literals duplicated                                                                                                               | AvoidDuplicateLiterals       | Store them in a variable                                      | 4           |
| 92 ; 113 ; 180       | Local variable could be made final                                                                                                | LocalVariableCouldBeFinal    | Add final in declaration                                      | 3           |
| 171                   | The method 'getTransferInput(String)' has a cognitive complexity of 24, current threshold is 15                                   | CognitiveComplexity          | Reduce method complexity - consider splitting functionalities | 1           |
| 171                   | The method 'getTransferInput(String)' has a cyclomatic complexity of 15.                                                          | CyclomaticComplexity         | Reduce cyclomatic complexity                                  | 1           |
| 175                   | Position literals first in String comparisons                                                                                     | LiteralsFirstInComparisons   | Swap comparison                                               | 3           |

## Summary of Reccomendation
Defects found in the code belongs mainly to 3 categories: Logic, Code Clarity and Ease of Modification. 
For each category, follows a summary of the reccomendations: 
- Logic: to improve code quality the developer should remove erroneous constructors, should make customerNumber and customerPin final and should provide a mean for the user to terminate the banking operations
- Code Clarity: to improve code quality, method's names should be made more explicative and coherent with the implementation
- Ease of Modification: to improve code quality, method's functionalities should be split into submethods, so to increase code reusability and code decoupling.

## Review Time and Defects Found
Overall each member spent 2 hours reviewing the code: 1.5h for the manual review and 0.5h for the automatic. 
As could be noticed from the tables above, on one side the automatic tools succeedes in finding subdle errors, such as ***unused declaration*** or ***local variable could be made final***; on the other side, manual review allows to discover posible security threats (**customerNumber** modifiable) and bad architectural design. 
Combining the two reivews together allowed to capture as much errors as possible within the code.