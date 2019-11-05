package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

class CPU extends Sim_entity {
    private Sim_port in, out1, out2;
    private Sim_normal_obj delay;
    private Sim_random_obj prob;
    private Sim_stat stat;

    CPU(String name, double mean, double var) {
        super(name);
        in = new Sim_port("in");
        out1 = new Sim_port("out1");
        out2 = new Sim_port("out2");
        add_port(in);
        add_port(out1);
        add_port(out2);
        delay = new Sim_normal_obj("Delay", mean, var);
        prob = new Sim_random_obj("Probability");
        add_generator(delay);
        add_generator(prob);

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.THROUGHPUT);
        stat.add_measure(Sim_stat.RESIDENCE_TIME);
        set_stat(stat);
    }

    public void body() {
        while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(e);
            sim_process(delay.sample());
            sim_completed(e);
            double p = prob.sample();
            if (p <= 0.50) {
                sim_schedule(out1, 0.0, 1);
            } else {
                sim_schedule(out2, 0.0, 1);
            }
        }
    }
}