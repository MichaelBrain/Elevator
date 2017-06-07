package aston.jpc;

import java.util.Scanner;

/**
 * Provides an interface with the user.
 */
class UserInterface {
    private Main app;
    private Scanner in;

    /**
     * @param app Main
     */
    UserInterface(Main app) {
        setApp(app);
        setIn(new Scanner(System.in));
    }

    /**
     * Presents a menu for the user.
     */
    void run() {
        System.out.println("Welcome to the Elevator Simulator. Enter one of the below options to begin:");
        int menuSelection = displayMenu();
        processSelection(menuSelection);
    }

    /**
     * Displays a menu and returns the selection.
     *
     * @return int menu selection.
     */
    private int displayMenu() {
        System.out.println("[1] - Start Simulation");
        System.out.println("[2] - Quit Application");
        System.out.print("Enter Option: ");
        while (!getIn().hasNextInt()) {
            System.err.println("Please enter a valid option integer (1 or 2).");
            getIn().next();
        }

        return getIn().nextInt();
    }

    /**
     * Processes the menu selection.
     *
     * @param menuSelection int menu selection.
     */
    private void processSelection(int menuSelection) {
        switch (menuSelection) {
            case 1:
                startSimulation();
                finish();
                break;
            case 2:
                System.out.println("Application quit.");
                break;
            default:
                System.err.println("Option not recognised, please try again.");
                run();
        }
    }

    /**
     * Retrieves user input values and passes back to the Main class to initiate a Simulation.
     */
    private void startSimulation() {
        System.out.println("You have chosen 'Start Simulation'.");

        int numEmployees = getIntInput("Employees");

        int numDevelopers = getIntInput("Developers");

        float p = getFloatInput("p");

        float q = getFloatInput("q");

        long seed = getLongInput("Seed");

        System.out.println("Running simulation...");

        getApp().start(numEmployees, numDevelopers, p, q, seed);
    }

    /**
     * Checks the Simulation success field and presents the equivalent result message to the user.
     *
     * @param simulationResult Simulation
     */
    void complete(Simulation simulationResult) {
        if (simulationResult.isSuccess()) {
            // Simulation succeeded, check file for statistics
            System.out.println("Success - simulation completed for " + simulationResult.getTick() + " ticks, please check " + simulationResult.getFileName() + " for statistics.");
        } else {
            // Simulation failed, check error log
            System.err.println("Error: " + simulationResult.getErrorMsg());
        }
    }

    /**
     * Presents an opportunity for the user to restart the simulation.
     */
    private void finish() {
        System.out.println("Would you like to restart the simulation?");
        int menuSelection = displayMenu();
        processSelection(menuSelection);
    }

    /**
     * Retrieves and returns an int input from the user.
     *
     * @param entity String representing entity for value retrieval.
     * @return int value for entity.
     */
    private int getIntInput(String entity) {
        System.out.print("Number of " + entity + ": ");
        while (!getIn().hasNextInt()) {
            System.err.println("Please enter a valid number.");
            System.out.print("Number of " + entity + ": ");
            getIn().next();
        }
        return getIn().nextInt();
    }

    /**
     * Retrieves and returns a Float input from the user.
     *
     * @param entity String representing entity for value retrieval.
     * @return Float value for entity.
     */
    private float getFloatInput(String entity) {
        System.out.print("Value of " + entity + ": ");
        while (!getIn().hasNextFloat()) {
            System.err.println("Please enter a valid decimal number.");
            System.out.print("Value of " + entity + ": ");
            getIn().next();
        }
        return getIn().nextFloat();
    }

    /**
     * Retrieves and returns a long input from the user.
     *
     * @param entity String representing entity for value retrieval.
     * @return long value for entity.
     */
    private long getLongInput(String entity) {
        System.out.print("Value of " + entity + ": ");
        while (!getIn().hasNextLong()) {
            System.err.println("Please enter a valid decimal number.");
            System.out.print("Value of " + entity + ": ");
            getIn().next();
        }
        return getIn().nextLong();
    }

    /**
     * Getter for app field.
     *
     * @return Main app.
     */
    private Main getApp() {
        return app;
    }

    /**
     * Setter for app field.
     *
     * @param app Main app.
     */
    private void setApp(Main app) {
        this.app = app;
    }

    /**
     * Getter for in field.
     *
     * @return Scanner in.
     */
    private Scanner getIn() {
        return in;
    }

    /**
     * Setter for in field.
     *
     * @param in Scanner.
     */
    private void setIn(Scanner in) {
        this.in = in;
    }
}
