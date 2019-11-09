package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class LeaveBags extends Sim_entity {
    private Sim_port in, out;
    private Sim_negexp_obj delay;
    // private Sim_stat stat;

    LeaveBags(String name, double meanPerBag) {
        super(name);
        in = new Sim_port("in");
        out = new Sim_port("out");
        add_port(in);
        add_port(out);
        delay = new Sim_negexp_obj("Delay", meanPerBag);
        add_generator(delay);
    }

    public void body() {
        Sim_event e = new Sim_event();
        sim_get_next(e);
        while (Sim_system.running()) {
            Person person = (Person) e.get_data();
            double totalWait = 0;
            for (int i = 0; i < person.get_numberOfBags(); i++) {
                totalWait += delay.sample();
            }
            sim_process(totalWait);
            sim_completed(e);
            sim_schedule(out, 0.0, 0, person);
            e = new Sim_event();
            sim_get_next(e);
        }
    }
}