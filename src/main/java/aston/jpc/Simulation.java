package aston.jpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

public class Simulation {
    private final int seed = 10;
    boolean success;
    String errorMsg;
    float p, q;
    int tick = 1;
    PrintWriter file;
    String fileName;
    Random dice = new Random(this.seed);

    Building getBuilding() {
        return building;
    }

    private Building building;

    public Simulation(int numEmp, int numDev, float p, float q) {
        this.p = p;
        this.q = q;
        boolean prepareResult = prepare(numEmp, numDev);
        if (prepareResult) {
            boolean prepareFileResult = prepareFile();
            if (prepareFileResult) {
                boolean runResult = run();
                if (runResult) {
                    this.success = true;
                    this.file.println("*** SIMULATION ENDED ***");
                    this.file.close();
                } else {
                    this.success = false;
                    this.errorMsg = "Running simulation failed";
                }
            } else {
                this.success = false;
                this.errorMsg = "Preparing file failed.";
            }
        } else {
            this.success = false;
            this.errorMsg = "Preparing simulation failed.";
        }
    }

    private boolean prepare(int numEmp, int numDev) {
        this.building = new Building(numEmp, numDev, this);

        return true;
    }

    private boolean prepareFile() {
        try {
            this.fileName = "SimulationResult-" + new Date().getTime() + ".txt";
            this.file = new PrintWriter(fileName, "UTF-8");
            this.file.println("*** SIMULATION STARTED ***");
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private Boolean run() {
        // Begin 8 hour day
        // Employees and Developers begin at ground level and immediately decide a floor.
        while (tick < 2880) {
            // People decide
            for (Person person : building.getPeople()) {
                person.decide(this);
            }
            // Elevator decides
            building.getElevator().decide();
            // Go to next tick
            tick++;
        }

        return true;
    }
}