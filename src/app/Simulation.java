package app;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import eduni.simjava.*;
import eduni.simjava.distributions.*;

public class Simulation {

    private static double LAMBDA = 50.0; // Arrival rate
    private static double DELTA = 15.0; // Deposit bag rate
    private static double OMEGA = 10.0; // Person screen rate
    private static double SIGMA_GAMMA = 5.0; // Bag screen rate
    private static double SIGMA = 20.0; // Bag search rate
    private static double GAMMA = 15.0; // Bag pickup rate

    private static Map<Integer, Integer> personCountPerBagCount;
    private static Map<Integer, Double> residenceTimePerBagCount;

    public static void main(String[] args) {
        Sim_system.initialise();

        Source source = new Source("Source", LAMBDA, 2);
        LeaveBags leaveBags = new LeaveBags("LeaveBags", DELTA);
        Screen screen = new Screen("Screen", (SIGMA_GAMMA), OMEGA);
        BagPickup bagPickup = new BagPickup("BagPickup", GAMMA);
        BagInspection bagInspection = new BagInspection("BagInspection", SIGMA);

        Sim_system.link_ports("Source", "out", "LeaveBags", "in");
        Sim_system.link_ports("LeaveBags", "out", "Screen", "in");
        Sim_system.link_ports("Screen", "out", "BagPickup", "in");
        Sim_system.link_ports("BagPickup", "out", "BagInspection", "in");
        Sim_system.run();

        personCountPerBagCount = new HashMap<>();
        residenceTimePerBagCount = new HashMap<>();
        for (Person person : source.travellers) {
            int bagCount = person.get_numberOfBags();
            if (!personCountPerBagCount.containsKey(bagCount)) {
                personCountPerBagCount.put(bagCount, 0);
                residenceTimePerBagCount.put(bagCount, 0.0);
            }
            int personCount = personCountPerBagCount.get(bagCount);
            double residenceTime = residenceTimePerBagCount.get(bagCount);
            personCountPerBagCount.put(bagCount, personCount + 1);
            residenceTimePerBagCount.put(bagCount, residenceTime + person.get_residenceTime());
        }
        try {
            FileWriter writer = new FileWriter("sim_report", true);
            for (int key : personCountPerBagCount.keySet()) {
                int personCount = personCountPerBagCount.get(key);
                double residenceTime = residenceTimePerBagCount.get(key);

                writer.append(String.format("Travellers with %d bags, have an average residence time of: %f\n", key,
                        residenceTime / personCount));
            }
            writer.close();
            writer.flush();
        } catch (Exception e) {

        }

    }
}