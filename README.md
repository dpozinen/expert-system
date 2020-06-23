# expert-system

This project requires us to create an expert system for calculating proposals, in other words a program that can reason about a set of rules and initial facts in order to deduce certain other facts.

This is the second project of the Advanced Algorithms branch at Unit Factory (School 42 Paris).

# what it does
given input of

```
A + B => C # if A and B then C
D => B # if D then B

=AD # A and D are true

?C # what is C
```
will output ```true``` because D is true, so then B is true. A is given as true, so C is as well.

Full subject can be found [here](https://github.com/Binary-Hackers/42_Subjects/blob/master/00_Projects/02_Algorithmic/expertsystem.pdf)

Other operators:
```
* | or
* ^ xor
* ! not
* () braces -> !(A + B) vs !A + B
```
