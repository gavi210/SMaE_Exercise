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

This dimensions could be customized and are specified in the [PMD_RuleSet](/Code/PMD_RuleSet.xml) file.



# Defects Found
Follows a description of each fault found per java file.

## OptionMenu.java
For the OptionMenu class the review with the help of the automatic tool PMD was done first.
This first review focused on best practices, code style and documentation.
Complexity issues were ignored, because the reviewer thought that the thresholds were set too low.
The manual review was done after and focused on design, the program being error prone and general bugs.

### Manually detected Defects
| Lines                                                               | Defect                                                                                                                       | Defect Type          | Recommendation                                                                                                                        | Occurences  |
|---------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------|-------------|
| 9                                                                   | The OptionMenu class in this simple example shouldn't be initialized twice since the customer data is not a static variable. | Design Decision      | Implement the Singleton pattern using a private constructor and a public getter.                                                      | 1           |
| 14, 155, 185                                                        | All the function should be terminatable.                                                                                     | Logic                | Add functionality to leave the process of login into an account, creating an account and choosing between those.                      | 3           |
| 38                                                                  | If user enters a not a number then the program ends in an infinite loop.                                                     | Logic                | Add "menuInput.next();" on the next line, else the program runs into an infinite loop.                                                | 1           |
| 56, 59, 62, 90, 93, 96, 100, 103, 129, 132, 135, 138, 141, 195, 199 | Switch statement with hardcoded integers                                                                                     | Ease of Modification | Create an integer variable to represent a choice that also can be used, when asking for input.                                        | 3           |
| 75, 117                                                             | A lot of duplicated code for checking and savings, because they behave the same.                                             | Duplicate Code       | Combine getChecking and getSaving members to one function taking an interface as argument. See further details in the Account review. | 2           |
| 48-50, 80-84, 122-126, 191-192                                      | For the ouput the same format is often reused but hardcoded in the source code.                                              | Ease of Modification | Create a format function that can be used when user input is asked.                                                                   | 4           |
| 156                                                                 | The user can enter a negative number as customer or pin number                                                               | Logic                | Permit users to chose non-positive customer number and pin.                                                                           | 1           |

### Automatically detected Defects
| Lines                                    | Defect                                                                            | Defect Type                     | Recommendation                                                                                                                               | Occurences  |
|------------------------------------------|-----------------------------------------------------------------------------------|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| 9                                        | The class is not inside a package.                                                | NoPackage                       | Add class to a package.                                                                                                                      | 1           |
| 9, 10, 11, 12, 14, 43, 75, 117, 155, 185 | The class is barely commented.                                                    | CommentRequired                 | Comment classes, fields and public methods.                                                                                                  | 10          |
| 9                                        | The class uses the default constructor.                                           | AtLeastOneConstructor           | Implement at least one constructor per class. Do not rely on default constructors.                                                           | 1           |
| 12                                       | A concrete instance of HashMap is used.                                           | LooseCoupling                   | Use interface (Map) instead of implementation type (HashMap).                                                                                | 1           |
| 14, 43, 75, 117                          | The getter function in this class do not return anything.                         | LinguisticNaming                | Rename the function.                                                                                                                         | 4           |
| 24, 161                                  | The variable name is not expressive enough, because it is too short.              | ShortVariable                   | Chose longer and more expressive names for variables. E.g. "iter" instead of "it".                                                           | 2           |
| 43, 75, 117                              | Method argument shouldn't get changed inside the function but could be.           | MethodArgumentCouldBeFinal      | Declare the method argument final, because it is not assigned.                                                                               | 3           |
| 26, 27, 53, 87, 128, 164, 178            | The local variables on those line shouldn't get changed but technically could be. | LocalVariableCouldBeFinal       | Declare those variables final. All of them except the one at line 177 are inside a loop. An alternative is to move them outside of the loop. | 7           |
| Many lines                               | All over the code the same literals are used or similar ones.                     | AvoidDuplicateLiterals          | Assign the strings you use more than once to variables instead of rewriting the literal.                                                     | A lot       |
| 164                                      | There is a redundant variable.                                                    | UnusedLocalVariable             | Remove the variable from program.                                                                                                            | 1           |
| 186, 187                                 | Large numbers in source code are not easilky readable.                            | UseUnderscoresInNumericLiterals | Use underscores to separate every third digit to make large numbers more readable.                                                           | 5           |
| 214                                      | Terminating the JVM is bad practice.                                              | DoNotTerminateVM                | Let the main function terminate the program. E.g. Use a return value to signal the main function to terminate the program.                   | 1           |


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
