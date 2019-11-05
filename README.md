# airport-security-check-point-simulation
 
# What to do:
You are asked to construct a simulation model representing an airport security check-point. Passengers arrive, at rate λ, at the check-point and place their bags on a conveyor belt for screening. 75% of passengers have a single item of hand-baggage, 20% have two items and the remaining passengers have three items. Depositing each item on the conveyor belt takes approximately 1/d seconds (action deposit). Passengers then walk, at rate w through the metal detector. Approximately 1 in 5 passengers are additionally searched, taking 1/s seconds on average (action search). Finally the passengers must collect her bag(s): picking up a bag takes approximately 1/c seconds. A passenger who deposits two bags must collect two bags. The conveyor belt accepts bags and then screens them, taking 1/sc seconds on average.

1. Postulate appropriate values for λ, d, w, s and c.
2. Construct an SimJava model of the security checkpoint.
3. Run an appropriate number of times the model and go through the results.
4. Calculate the average residence time of each type of passenger, including those that are additionally searched.

# How to compile
javac -classpath ./classes/ ./classes/Simulation.java

# How to run
java -classpath ./classes/ Simulation 