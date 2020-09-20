# UCT CSC2002S assignment 2 : Concurrency

This is an application for demostrating the use of concurrency with a terrain-water flow simulator. 
This is an assignment for the UCT CSC2002S course.

# How to use.
The user have to navigate to the main directory of this project.


First you have to compile the java files using make.
```bash
make
```
Then after the files have been compiled one can choose to use a medium size sample or a
large size one.

### running with medium sample
```bash
make runM
```
### running with large sample
```bash
make runL
```

## Simulation
Water can be added with a mouse click on the terrain. The simulation can be played, paused, reset and exit through buttons provided on the GUI.

## Internal Testing
Monitor console output after exit, it will show either "water conserved" or "water not conserved" to indicate whether the simulation ran correctly without any concurrency issues.
