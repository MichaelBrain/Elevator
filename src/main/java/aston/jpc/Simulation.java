package aston.jpc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

/**
 * Controls the execution of the simulation and creates all object instances required.
 */
class Simulation {
    private final boolean success;
    private final float p, q;
    private final long seed;
    private final Random dice;
    private String errorMsg;
    private int tick = 1;
    private PrintWriter file;
    private String fileName;
    private Building building;

    /**
     * @param numEmp int
     * @param numDev int
     * @param p float
     * @param q float
     * @param seed long
     */
    Simulation(int numEmp, int numDev, float p, float q, long seed) {
        this.p = p;
        this.q = q;
        this.seed = seed;
        this.dice = new Random(getSeed());

        boolean prepareResult = prepare(numEmp, numDev);

        if (prepareResult) {
            boolean prepareFileResult = prepareFile();
            if (prepareFileResult) {
                boolean runResult = run();
                if (runResult) {
                    this.success = true;
                    getFile().println("*** SIMULATION ENDED ***");
                    getFile().close();
                } else {
                    this.success = false;
                    setErrorMsg("Running simulation failed");
                }
            } else {
                this.success = false;
                setErrorMsg("Preparing file failed.");
            }
        } else {
            this.success = false;
            setErrorMsg("Preparing simulation failed.");
        }
    }

    /**
     * Creates a Building instance and adds the specified number of Employees and Developers.
     *
     * @param numEmp int number of Employees to add.
     * @param numDev int number of Developers to add.
     *
     * @return boolean true if complete, false if failures.
     */
    private boolean prepare(int numEmp, int numDev) {
        setBuilding(new Building(this));

        getBuilding().addEmployees(numEmp);
        getBuilding().addDevelopers(numDev);

        return true;
    }

    /**
     * Makes a File instance ready to be written to.
     *
     * @return boolean true if complete, false if failures.
     */
    private boolean prepareFile() {
        try {
            setFileName("SimulationResult-" + new Date().getTime() + ".txt");
            setFile(new PrintWriter(new FileWriter(getFileName(), true)));
            getFile().println("*** SIMULATION STARTED ***");
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Loops through each tick and triggers various decisions.
     *
     * @return boolean true if complete, false if failures.
     */
    private Boolean run() {
        // Begin 8 hour day
        while (getTick() < 28800) {
            // People decide
            for (int i = 0; i < getBuilding().getPeople().size(); i++) {
                Person person = getBuilding().getPeople().get(i);
                person.decide();
            }

            // Elevator decides
            getBuilding().getElevator().decide();

            // Building decides
            getBuilding().decide();

            // Go to next tick
            setTick(getTick() + 1);
        }

        return true;
    }

    /**
     * Getter for building field.
     *
     * @return Building building.
     */
    Building getBuilding() {
        return building;
    }

    /**
     * Setter for building field.
     *
     * @param building Building.
     */
    private void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Getter for success field.
     *
     * @return true if Simulation completed with no errors, false if not.
     */
    boolean isSuccess() {
        return success;
    }

    /**
     * Getter for errorMsg field.
     *
     * @return String error message from Simulation error.
     */
    String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter for errorMsg field.
     *
     * @param errorMsg String error message to send to user.
     */
    private void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Getter for p field.
     *
     * @return float p.
     */
    float getP() {
        return p;
    }

    /**
     * Getter for q field.
     *
     * @return float q.
     */
    float getQ() {
        return q;
    }

    /**
     * Getter for tick field.
     *
     * @return int the current tick.
     */
    int getTick() {
        return tick;
    }

    /**
     * Setter for tick field.
     *
     * @param tick int the current tick.
     */
    private void setTick(int tick) {
        this.tick = tick;
    }

    /**
     * Getter for file field.
     *
     * @return PrintWriter the file being written to.
     */
    PrintWriter getFile() {
        return file;
    }

    /**
     * Setter for file field.
     *
     * @param file PrintWriter the file being written to.
     */
    private void setFile(PrintWriter file) {
        this.file = file;
    }

    /**
     * Getter for fileName field.
     *
     * @return String the name of the file being written to.
     */
    String getFileName() {
        return fileName;
    }

    /**
     * Setter for fileName field.
     *
     * @param fileName String the name of the file being written to.
     */
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter for seed field.
     *
     * @return long the seed used for the random number generator.
     */
    private long getSeed() {
        return seed;
    }

    /**
     * Getter for dice field.
     *
     * @return Random simulation of a number generation.
     */
    Random getDice() {
        return dice;
    }
}
