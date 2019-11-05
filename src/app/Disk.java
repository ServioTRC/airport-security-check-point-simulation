package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class Disk extends Sim_entity {
    private Sim_port in;
    private Sim_normal_obj delay;
    private Sim_stat stat;

    Disk(String name, double mean, double var) {
        super(name);
        in = new Sim_port("in");
        add_port(in);
        // Create the disk's distribution and add it
        delay = new Sim_normal_obj("Delay", mean, var);
        add_generator(delay);

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        set_stat(stat);
    }

    public void body() {
        while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(e);
            // Sample the distribution
            sim_process(delay.sample());
            sim_completed(e);
        }
    }
}