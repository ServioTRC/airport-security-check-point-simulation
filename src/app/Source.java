package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class Source extends Sim_entity {
    private Sim_port out;
    private Sim_negexp_obj delay;

    Source(String name, double mean) {
        super(name);
        out = new Sim_port("out");
        add_port(out);
        delay = new Sim_negexp_obj("Delay", mean);
        add_generator(delay);
    }

    public void body() {
        for (int i = 0; i < 100; i++) {
            sim_schedule(out, 0.0, 0);
            sim_pause(delay.sample());
        }
    }
}