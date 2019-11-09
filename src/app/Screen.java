package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class Screen extends Sim_entity {
    private Sim_port in, out;
    private Sim_negexp_obj delayBag, delayPerson;

    public Screen(String name, double meanPerBag, double meanPerPerson) {
        super(name);
        in = new Sim_port("in");
        out = new Sim_port("out");
        add_port(in);
        add_port(out);
        delayBag = new Sim_negexp_obj("DelayBag", meanPerBag);
        delayPerson = new Sim_negexp_obj("DelayPerson", meanPerPerson);
        add_generator(delayBag);
        add_generator(delayPerson);
    }

    public void body() {
        Sim_event e = new Sim_event();
        sim_get_next(e);
        while (Sim_system.running()) {
            Person person = (Person) e.get_data();
            double waitPerson = delayPerson.sample();
            double totalWaitBags = 0;
            for (int i = 0; i < person.get_numberOfBags(); i++) {
                totalWaitBags += delayBag.sample();
            }
            double maxWait = Math.max(totalWaitBags, waitPerson);
            sim_process(maxWait);
            sim_completed(e);
            sim_schedule(out, 0.0, 0, person);
            e = new Sim_event();
            sim_get_next(e);
        }
    }
}