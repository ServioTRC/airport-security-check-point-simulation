package app;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

public class Simulation {
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