package aston.jpc;

import java.util.Scanner;

class UserInterface {
    private Main app;
    private Scanner in;

    UserInterface(Main app) {
        this.app = app;
        this.in = new Scanner(System.in);
    }

    void run() {
        System.out.println("Welcome to the Elevator Simulator. Enter one of the below options to begin:");
        int menuSelection = displayMenu();
        processSelection(menuSelection);
    }

    private int displayMenu() {
        System.out.println("[1] - Start Simulation");
        System.out.println("[2] - Quit Application");
        System.out.print("Enter Option: ");
        while (!in.hasNextInt()) {
            System.err.println("Please enter a valid option integer (1 or 2).");
            in.next();
        }

        return in.nextInt();
    }

    private void processSelection(int menuSelection) {
        switch (menuSelection) {
            case 1:
                this.startSimulation();
                this.finish();
                break;
            case 2:
                System.out.println("Application quit.");
                break;
            default:
                System.err.println("Option not recognised, please try again.");
                this.run();
        }
    }

    private void startSimulation() {
        System.out.println("You have chosen 'Start Simulation'.");

        int numEmployees = getIntInput("Employees");

        int numDevelopers = getIntInput("Developers");

        float p = getFloatInput("p");

        float q = getFloatInput("q");

        long seed = getLongInput("Seed");

        System.out.println("Running simulation...");

        app.start(numEmployees, numDevelopers, p, q, seed);
    }

    void complete(Simulation simulationResult) {
        if (simulationResult.success) {
            // Simulation succeeded, check file for statistics
            System.out.println("Success - simulation completed for " + simulationResult.tick + " ticks, please check " + simulationResult.fileName + " for statistics.");
        } else {
            // Simulation failed, check error log
            System.err.println("Error: " + simulationResult.errorMsg);
        }
    }

    private void finish() {
        System.out.println("Would you like to restart the simulation?");
        int menuSelection = displayMenu();
        processSelection(menuSelection);
    }

    private int getIntInput(String entity) {
        System.out.print("Number of " + entity + ": ");
        while (!in.hasNextInt()) {
            System.err.println("Please enter a valid number.");
            System.out.print("Number of " + entity + ": ");
            in.next();
        }
        return in.nextInt();
    }

    private float getFloatInput(String entity) {
        System.out.print("Value of " + entity + ": ");
        while (!in.hasNextFloat()) {
            System.err.println("Please enter a valid decimal number.");
            System.out.print("Value of " + entity + ": ");
            in.next();
        }
        return in.nextFloat();
    }

    private long getLongInput(String entity) {
        System.out.print("Value of " + entity + ": ");
        while (!in.hasNextLong()) {
            System.err.println("Please enter a valid decimal number.");
            System.out.print("Value of " + entity + ": ");
            in.next();
        }
        return in.nextLong();
    }
}
