package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class BagInspection extends Sim_entity {
    private Sim_port in;
    private Sim_negexp_obj delayBag;

    public BagInspection(String name, double meanPerBag) {
        super(name);
        in = new Sim_port("in");
        add_port(in);
        delayBag = new Sim_negexp_obj("DelayBag", meanPerBag);
        add_generator(delayBag);
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
            person.end();
            e = new Sim_event();
            sim_get_next(e);
        }
    }
}