package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class BagPickup extends Sim_entity {
    private Sim_port in, out;
    private Sim_negexp_obj delayBag;
    private Sim_random_obj probability;

    public BagPickup(String name, double meanPerBag) {
        super(name);
        in = new Sim_port("in");
        out = new Sim_port("out");
        add_port(in);
        add_port(out);
        delayBag = new Sim_negexp_obj("DelayBag", meanPerBag);
        probability = new Sim_random_obj("DelayPerson");
        add_generator(delayBag);
        add_generator(probability);
    }

    public void body() {
        Sim_event e = new Sim_event();
        sim_get_next(e);
        while (Sim_system.running()) {
            Person person = (Person) e.get_data();
            double totalWaitBags = 0;
            for (int i = 0; i < person.get_numberOfBags(); i++) {
                totalWaitBags += delayBag.sample();
            }
            sim_process(totalWaitBags);
            sim_completed(e);
            if (probability.sample() > 0.8) {
                sim_schedule(out, 0.0, 0, person);
            } else {
                person.end();
            }
            e = new Sim_event();
            sim_get_next(e);
        }
    }
}