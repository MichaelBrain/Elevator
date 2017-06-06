package aston.jpc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

public class Simulation {
    private long seed;
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

    public Simulation(int numEmp, int numDev, float p, float q, long seed) {
        this.p = p;
        this.q = q;
        this.seed = seed;
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
        this.building = new Building(this);

        this.building.addEmployees(numEmp);
        this.building.addDevelopers(numDev);

        return true;
    }

    private boolean prepareFile() {
        try {
            this.fileName = "SimulationResult-" + new Date().getTime() + ".txt";
            this.file = new PrintWriter(new FileWriter(fileName, true));
            this.file.println("*** SIMULATION STARTED ***");
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private Boolean run() {
        // Begin 8 hour day
        // Employees and Developers begin at ground level and immediately decide a floor.
        while (tick < 28800) {
            // People decide
            for (int i = 0; i < building.getPeople().size(); i++) {
                Person person = building.getPeople().get(i);
                person.decide();
            }
            // Elevator decides
            building.getElevator().decide();
            building.decide();
            // Go to next tick
            tick++;
        }

        return true;
    }
}
