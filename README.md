# OBJ2100_Gruppe7

This program was built as a required assignment in Object-Oriented Programming 2 at the University of South-Eastern Norway. This assignment was required to have approved to be allowed entry to the final exam. We did get approved, but the program is far from finished and lacks polish and final programming for the functionality which was asked for in the assignment.

The assignment was to create a simulation of a running restaurant. Customers will come in and order one of 4 - 5 available dishes, which the chefs then would prepare. The preparing time is simulated with threads, which will sleep for a given time depending on the dish. The chefs and customers run on their own threads. The threads are manipulated in the code to simulate time to prepare dish, chefs resting and preparing to make a new dish, and customers waiting. 

There is a happy and not happy counter which will update, depending on the customers total waiting time after ordering a dish.

The assignment was group-based with five people in each group.

For this course, Java was chosen programming language, which means the entire program is written in Java. There is no database for this program, the only storing is happening in a txt-file which acts as a log.

Screenshot of the running program will follow:

![Running restaurant simulation](/image-sim.png)