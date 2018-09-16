# DeloitteScheduler

Scheduling of agents to tasks, in this case, activities to teams, is a combinatorial optimisation problem.

I solved this problem by modelling it as a directed acyclic graph, then using iteratively deepening A* search to
search the solution space for an optimal solution.

I used heuristics to help guide the search in the right direction. Schedules in which the lunch or presentation times
didn't align with the rules scored badly in the "evaluate" function in the Schedule class. This gave them a high cost
which meant the algorithm would explore other paths (that would have hopefully) better prospects.

# Installation (Requires java8)
    - `git clone https://github.com/JamesArthurHolland/DeloitteSchedule`
    - `cd src/com/company`
    - `javac *.java && java -classpath ../../ com.company.Main`