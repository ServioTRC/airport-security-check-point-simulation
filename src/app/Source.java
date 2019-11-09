package app;

import java.util.LinkedList;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class Source extends Sim_entity {
    private Sim_port out;
    private Sim_negexp_obj delay;
    private Sim_uniform_obj bagProbability;
    private int eventCount;

    public LinkedList<Person> travellers;

    public Source(String name, double mean, int eventCount) {
        super(name);
        out = new Sim_port("out");
        add_port(out);
        delay = new Sim_negexp_obj("Delay", mean);
        bagProbability = new Sim_uniform_obj("Bag probability", 0, 1);
        add_generator(delay);
        this.eventCount = eventCount;
        travellers = new LinkedList<Person>();
    }

    public Source(String name, double mean) {
        this(name, mean, 100);
    }

    public void body() {
        double probability;
        int bagCount;

        for (int i = 0; i < eventCount; i++) {
            probability = bagProbability.sample();
            if (probability <= 0.75) {
                bagCount = 1;
            } else if (probability <= 0.95) {
                bagCount = 2;
            } else {
                bagCount = 3;
            }
            Person person = new Person(String.format("Person%d", i), bagCount);
            travellers.add(person);
            person.start();
            sim_schedule(out, 0.0, 0, person);
            sim_pause(delay.sample());
        }
    }
}