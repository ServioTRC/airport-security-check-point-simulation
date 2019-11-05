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

class Simulation {
  public static void main(String[] args) {
    Sim_system.initialise();
    Source source = new Source("Source", 1000);
    CPU processor = new CPU("CPU", 50.0, 25.0);
    Disk disk1 = new Disk("Disk1", 30.0, 25.0);
    Disk disk2 = new Disk("Disk2", 10.0, 25.5);
    Sim_system.link_ports("Source", "out", "CPU", "in");
    Sim_system.link_ports("CPU", "out1", "Disk1", "in");
    Sim_system.link_ports("CPU", "out2", "Disk2", "in");
    Sim_system.run();
  }
}