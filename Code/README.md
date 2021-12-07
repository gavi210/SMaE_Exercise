# Project Description
The students in this team did not have an own java project at hand, because they were either stored in unavailable 
locations or already have been reviewed using an automatic tool. It was decided that the students look at a small project available on Github.
After a search for a suitable project it was decided to look at the source code available at: https://github.com/rajyash1904/ATM-Machine .

This project simulates an Automated Teller Machine.
Information of the customers taken into account are: **Account Number**, **Password**, and **Bank Account**. 
Operations supported by the ATM are:  **withdraw**, **deposit**, **view account balance** and **transfer funds**.
The ATM is composed by three simple classes: 
- **Account** provides attributes and methods to support user operations (withdraw, deposit, balance's view and transfer funds).
- **ATM** is the main project entry point.
- **OptionMenu** supports the user interaction with the ATM machine. The interaction is done via simple command line interface.

## Files under Review
Since the project is very simple, objective of the review would be to review the implementation of all the two above mentioned classes, which encapsule functionality: 
1. [Account](/ATM/Account.java)
2. [OptionMenu](/ATM/OptionMenu.java)

# Roles in the Project
Students participating in the exercise are [Maximilian Huwyler](mailto:maximilian.huwyler@uzh.ch) and [Riccardo Rigoni](mailto:riccardo.rigoni@uzh.ch). 
Both participants will act as ***reviewers*** and ***report writers***. 

# Review Methodology
Maximilian Huwyler will analyse the [OptionMenu.java](/ATM/OptionMenu.java) file, and Riccardo Rigoni the [Account.java](/ATM/Account.java) file.

Reviewers process will be done as follow:
1. **Initial Code Overview**: The goal is to properly understand the code behaviour.
2. **Manual identification of aspects to be improved**: With the knowledge of the code behaviour, lack in code quality could be detected. The dimentions of code quality investigated are mentioned below.
3. **Identification of Warnings with automatic tools**: The tool [PMD](https://pmd.github.io) is used for the assisted investigation of defects in the code.
4. **Suggestion for Improvements**: The Reviewer suggest possible code changes.

## Manually Investigated Quality Dimensions
During the manual review the reviewers will focus on: 
- **Code Clarity**: Variable and method name, code organization, indentation.
- **Ease of Modification**: How easily would be to change the code.
- **Logic**: Are all cases edge cases covered? Correctness of loop and branches. Meaningfullness of conditions.
- **Error Handling**: Correctness and soundness of error handling,
- **Design Decisions**: Code structure, architectural patterns used.


## PMD
As suggested in the project specification, the [PMD](https://pmd.github.io) tool is used to discover defects in the code. 
When running the PMD tool, the following set of predefined code quality dimensions are taken into account:
- Best practices
- Code style
- Design
- Error-proneness
- Documentation
- Performance
- Security

This dimensions could be customized and are specified in the [PMD_RuleSet](/Code/PMD_RuleSet.xml) file.

# Defects Found

## OptionMenu.java
For the OptionMenu class the review with the help of the automatic tool PMD was done first.
This first review focused on best practices, code style and documentation.
Complexity issues were ignored, because the reviewer thought that the thresholds were set too low.
The manual review was done after and focused on design decisions, logic and ease of modification.

### Manually Detected Defects
| Lines                                                               | Defect                                                                                                                       | Defect Type          | Recommendation                                                                                                                        | Occurences  |
|---------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------|-------------|
| 9                                                                   | The OptionMenu class in this simple example shouldn't be initialized twice since the customer data is not a static variable. | Design Decision      | Implement the Singleton pattern using a private constructor and a public getter.                                                      | 1           |
| 14; 155; 185                                                        | All the function should be terminatable.                                                                                     | Logic                | Add functionality to leave the process of login into an account, creating an account and choosing between those.                      | 3           |
| 38                                                                  | If user enters a not a number then the program ends in an infinite loop.                                                     | Logic                | Add "menuInput.next();" on the next line, else the program runs into an infinite loop.                                                | 1           |
| 56; 59; 62; 90; 93; 96; 100; 103; 129; 132; 135; 138; 141; 195; 199 | Switch statement with literals.                                                                                   | Ease of Modification | Create an integer variable to represent a choice that also can be used, when asking for input.                                        | 3           |
| 48-50; 80-84; 122-126; 191-192                                      | For the ouput the same format is often reused but inside literals in the source code.                                              | Ease of Modification | Create a format function that can be used when user input is asked.                                                                   | 4           |
| 156                                                                 | The user can enter a negative number as customer or pin number.                                                            | Logic                | Permit users to chose non-positive customer number and pin.                                                                           | 1           |

### Automatically Detected Defects
| Lines                                    | Defect                                                                            | Defect Type                     | Recommendation                                                                                                                               | Occurences  |
|------------------------------------------|-----------------------------------------------------------------------------------|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| 9; 10; 11; 12; 14; 43; 75; 117; 155; 185 | The class is barely commented.                                                    | CommentRequired                 | Comment classes, fields and public methods.                                                                                                  | 10          |
| 9                                        | The class uses the default constructor.                                           | AtLeastOneConstructor           | Implement at least one constructor per class. Do not rely on default constructors.                                                           | 1           |
| 12                                       | A concrete instance of HashMap is used.                                           | LooseCoupling                   | Use interface (Map) instead of implementation type (HashMap).                                                                                | 1           |
| 14; 43; 75; 117                          | The getter function in this class do not return anything.                         | LinguisticNaming                | Rename the function.                                                                                                                         | 4           |
| 24; 161                                  | The variable name is not expressive enough, because it is too short.              | ShortVariable                   | Chose longer and more expressive names for variables. E.g. "iter" instead of "it".                                                           | 2           |
| 43; 75; 117                              | Method argument shouldn't get changed inside the function but could be.           | MethodArgumentCouldBeFinal      | Declare the method argument final, because it is not assigned.                                                                               | 3           |
| 26; 27; 53; 87; 128; 164; 178            | The local variables on those line shouldn't get changed but technically could be. | LocalVariableCouldBeFinal       | Declare those variables final. All of them except the one at line 177 are inside a loop. An alternative is to move them outside of the loop. | 7           |
| Many lines                               | All over the code the same literals are used or similar ones.                     | AvoidDuplicateLiterals          | Assign the strings you use more than once to variables instead of rewriting the literal.                                                     | A lot       |
| 164                                      | There is a redundant variable.                                                    | UnusedLocalVariable             | Remove the variable from program.                                                                                                            | 1           |
| 186; 187                                 | Large numbers in source code are not easilky readable.                            | UseUnderscoresInNumericLiterals | Use underscores to separate every third digit to make large numbers more readable.                                                           | 5           |
| 214                                      | Terminating the JVM is bad practice.                                              | DoNotTerminateVM                | Let the main function terminate the program. E.g. Use a return value to signal the main function to terminate the program.                   | 1           |


## Account.java
From an initial view of the class emerges that the code is not well structured and not much ***cleaning*** has been done before submitting the code. Additionally, code is duplicated and important security threats have not been verified. 

### Manually Detected Defects
| Lines               | Defect                                                                                                                        | Defect Type          | Reccomendation                               | Occurrences |   
|---------------------|-------------------------------------------------------------------------------------------------------------------------------|----------------------|----------------------------------------------|-------------|
|  15 - 16            | Default constructor would allow to create empty account with no **pin** and **number** associated.                             | Logic                | Remove it.                                    | 1           |  
| 18 - 21             | Constructor will not assign value to **checkingBalance** and **savingBalance**.                                                | Logic                | Remove it.                                    | 1           |  
| 30 - 43             | customerNumber and customer pin should not be changed.                                                                         | Logic                | Remove Setters.                               | 2           |  
| 56 - 84             | There are unused methods.                                                                                                                | Code Clarity         | Remove methods.                                  | 6           |  
| 86 - 235            | Misleading method names.                                                                                                       | Code Clarity         | Rename methods.                                       | 5           |   
| 86 - 235            | The same methods do two actions: Interact with user and update balance's value.                                                | Ease of Modification | Consider to split into two different methods. | 6           |   
| 86 -235             | No way to exit from the loop if user wants to exit the operation.                                                              | Logic                | Provide a way to terminate the operation.     | 6           |  
| 86 - 235            | Lot of duplicate code.                                                                                                         | Ease of Modification | Refactor the methods to avoid duplicate.      | 6           |   
| 93 - 95 & 115 - 117 | Update of balance values is not atomic: If exception thrown during the update, balance values could result to be inconsistent. | Logic                | Make update operation safe.                   | 6           |   
| 171 - 235           | Method is too complicated.                      | Ease of Modification | Split into submethods. | 2 |  
| 141 - 235           | Duplicate code for user interaction.         | Ease of Maintenance & Design Decisions | Consider encapsulate user interaction and input validation in a method. | 6 |

### Automatically Detected Defects
| Lines                 | Defect                                                                                                                            | Defect Type                  | Reccomendation                                                | Occurrences |
|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------|------------------------------|---------------------------------------------------------------|-------------|
| -                     | Class, field and method comments are required.                                                                                 | CommentRequired              | Add Comments.                                                  | 14          |
| 9 - 10               | Avoid using redundant field initializer for 'checkingBalance' and 'savingBalance'.                                                 | RedundantFieldInitializer    | Remove redundant initializers.                                 | 2           |
| 12 - 13               | To avoid mistakes add a comment at the beginning of the input field if you want a default access modifier (2).                     | CommentDefaultAccessModifier | Add comments.                                                  | 2           |
| 15 - 29               | Add comments to constructors.                                                                                                      | Comment Required             | Add comments.                                                  | 3           |
| 30 - 39               | Linguistics Antipattern - The setter 'setCustomerNumber' and 'setPinNumber' should not return any type except void linguistically. | LinguisticNaming             | Rename methods.                                                | 2           |
| 57 ; 62 ; 67 ; 72        | There are useless parenthesis.                                                                                                   | UselessParetheses            | Remove them.                                                   | 4           |
| 86 ; 107 ; 128 ; 149 ; 171 | Linguistics Antipattern - The getter 'getCheckingWithdrawInput' should not return void linguistically.                             | LinguisticNaming             | Rename method.                                            | 5           |
| 90 ; 101 ; 111 ; 119 | Literals duplicated.                                                                                                               | AvoidDuplicateLiterals       | Store them in a variable.                                      | 4           |
| 92 ; 113 ; 180       | Local variable could be made final.                                                                                                | LocalVariableCouldBeFinal    | Add final in declaration.                                      | 3           |
| 171                   | The method 'getTransferInput(String)' has a cognitive complexity of 24, current threshold is 15.                                   | CognitiveComplexity          | Reduce method complexity - consider splitting functionalities. | 1           |
| 171                   | The method 'getTransferInput(String)' has a cyclomatic complexity of 15.                                                          | CyclomaticComplexity         | Reduce cyclomatic complexity.                                  | 1           |
| 175                   | Position literals first in String comparisons.                                                                                     | LiteralsFirstInComparisons   | Swap comparison.                                               | 3           |

## Summary of Recomendations

The defects found by the automatic tool were mostly code cosmetics and lack of documentation.
It is rather tedious to fix those defects but are definitely recommended to be fixed to reduce technical debt.
The other and more interesting defects found were: 
- missing Constructor,
- JVM not terminating (infinite loop).
- code complexity.
  
The former two are easy to fix, but the latter is a more complicated topic.
Automatic tools are great for generating more objective measures about complexities of functions.
In the end it depends on the reviewer and context, whether action will be taken or not.
In our study, Maximilian did not choose to lower complexity, because the threshold was too low in his opinion.
Riccardo on the other hand chose to lower the cognitive complexity of the function.
In such cases it could be helpful to have certain guidelines for reviewing code inside an organisation.
Such guidelines could include: 
- maximal function's complexity 
- dimensions of code defects investigated by automatic review tools.

Defects found manually in the code belong mainly to three categories: Logic, Code Clarity and Ease of Modification. 
For each category, follows a summary of the most important recommendations: 
- **Logic**: Add missing and remove erroneous constructors. Further provide means for the user to terminate the banking operations.
- **Code Clarity**: Make method names more explicative and coherent with the implementation.
- **Ease of Modification**: Avoid using too many literals. Split complex method into submethods depending on the functionalities, to increase code reusability and reduce coupling.

## Review Time and Defects Found
The student Maximilian Huwyler spent 2h reviewing and improving the code.
First 1h were spent improving the code using assisted by the automatic tool PMD.
Afterwards 1h were spent reviewing the code manually.
The student Riccardo Rigoni spent 2h reviewing the code: 1.5h for the manual review and 0.5h for the automatic.

As could be noticed from the tables above the automatic tool succeedes in finding subdle errors, such as ***unused declaration***, ***local variable could be made final*** or missing documentation.
Checking coding style with automated tools can be tedious, because fixing an error with one's own style could still be insufficient for the tool, leading to another additional review step.
Such a tool could be recommended to use before the manual review to find sudble errors and checking best practices, so one doesn't have to focus on them during manual review, which is time intensive.
Furthermore, developing a custom rulesets based on the adopted developers' best pratices is advised to avoid unnecessary warnings.

Manual review allows to discover more complicated defects like possible security threats (**customerNumber** modifiable) and bad architectural design.
Through manual review defects were found, which were not detected by the automatic tool.
This shows that the process of manual review is complementary to automatic review.

Timewise, we saw that the aspect of manually reviewing the code takes at least as long and but often longer than assisted reviewing.
We asked ourselves whether we could shorten the time of manually reviewing the code by conducting the assisted review first.
Unfortunately, from our investigation it cannot be concluded whether doing a review assisted by an automatic tool before the manual review can save significant time.
But we have seen that combining manual and automatic reviews together works well and allows to capture an extremly wide variety of defects within the code.

# Running the Project
To run the project\
1.Download all the code files (total 3)\
2.Store them in a single folder\
3.Either use your Java IDE e.g. Eclipse ,InteliJ or NetBeans\
4.Or in Windows open command prompt and go to project Directory\
1. run command : javac ATM.java\
2. After completion your folder will contain 3 .class files\
3. In command prompt run command: java ATM\
4. Project will start running
