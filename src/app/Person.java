package app;

import eduni.simjava.Sim_system;

public class Person {

    private String name;
    private int numberOfBags;
    private double startTime, endTime;
    private boolean initialized;

    public Person(String name, int numberOfBags) {
        this.name = name;
        this.numberOfBags = numberOfBags;
        this.initialized = false;
    }

    public void start() {
        startTime = Sim_system.sim_clock();
        initialized = true;
    }

    public void end() {
        if (!initialized) {
            throw new RuntimeException("Person was never started!");
        }
        endTime = Sim_system.sim_clock();
        initialized = true;
    }

    public String get_name() {
        return name;
    }

    public int get_numberOfBags() {
        return numberOfBags;
    }

    public double get_residenceTime() {
        return endTime - startTime;
    }
}